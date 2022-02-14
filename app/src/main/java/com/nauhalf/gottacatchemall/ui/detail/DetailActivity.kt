package com.nauhalf.gottacatchemall.ui.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.color.MaterialColors
import com.nauhalf.gottacatchemall.R
import com.nauhalf.gottacatchemall.core.adapter.PokemonStatAdapter
import com.nauhalf.gottacatchemall.core.adapter.PokemonStatTitleAdapter
import com.nauhalf.gottacatchemall.core.adapter.PokemonTypeAdapter
import com.nauhalf.gottacatchemall.core.base.BaseActivity
import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.ui.PokemonTypeItemDecoration
import com.nauhalf.gottacatchemall.core.utils.colorOfFirstType
import com.nauhalf.gottacatchemall.core.utils.showToast
import com.nauhalf.gottacatchemall.core.utils.toRealSizePokemon
import com.nauhalf.gottacatchemall.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {
    private lateinit var statTitleAdapter: PokemonStatTitleAdapter
    private lateinit var statAdapter: PokemonStatAdapter
    private lateinit var typeAdapter: PokemonTypeAdapter
    private val viewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pokemonData = intent.getParcelableExtra<Pokemon>(EXTRA_DATA)
        pokemonData?.let {
            setUp(pokemonData)
        } ?: run {
            finish()
        }
    }

    private fun setUp(pokemonData: Pokemon) {
        val color = pokemonData.types.colorOfFirstType()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        statTitleAdapter = PokemonStatTitleAdapter(color)
        statAdapter = PokemonStatAdapter(color)
        typeAdapter = PokemonTypeAdapter()
        binding.apply {
            rvStatTitle.adapter = statTitleAdapter
            rvStat.adapter = statAdapter
            rvPokemonType.addItemDecoration(
                PokemonTypeItemDecoration(
                    resources.getDimensionPixelSize(
                        R.dimen.medium
                    )
                )
            )
            rvPokemonType.adapter = typeAdapter

            ivFavorite.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.setFavorite().collect {
                        viewModel.setPokemon(it)
                        if (it.isFavorite) {
                            showToast("Thanks for giving them affection")
                        }
                    }
                }
            }
        }

        if (viewModel.pokemon.value == null) {
            viewModel.setPokemon(pokemonData)
            viewModel.getPokemonSpecies()
        }
        observe(pokemonData)
    }

    private fun observe(pokemonData: Pokemon) {
        viewModel.pokemon.observe(this) { pokemon ->
            if (pokemon != null) {
                binding.apply {
                    data = pokemon
                    Glide.with(this@DetailActivity)
                        .load(pokemon.imageUrl)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivPokemon)
                    val colorType = MaterialColors.getColor(root, pokemon.types.colorOfFirstType())

                    root.setBackgroundColor(colorType)
                    collapsing.contentScrim = ColorDrawable(colorType)
                    tvAbout.setTextColor(colorType)
                    tvBaseStats.setTextColor(colorType)
                    tvDescription.text = pokemon.description ?: "No Description"
                    tvPokemonId.text =
                        getString(R.string.poke_id, pokemon.id.toString().padStart(3, '0'))
                    tvWeightValue.text =
                        getString(R.string.poke_weight, pokemon.weight.toRealSizePokemon())
                    tvHeightValue.text =
                        getString(R.string.poke_height, pokemon.height.toRealSizePokemon())
                    statTitleAdapter.submitList(pokemon.stats)
                    statAdapter.submitList(pokemon.stats)
                    typeAdapter.submitList(pokemon.types)

                }
            }
        }

        viewModel.pokemonSpecies.observe(this) {
            when (it) {
                is Resource.Error -> {
                    binding.shimmerDescription.isVisible = false
                    binding.tvDescription.isVisible = true
                    showToast(it.message)
                }
                is Resource.Loading -> {
                    binding.shimmerDescription.isVisible = true
                    binding.tvDescription.isVisible = false
                }
                is Resource.Success -> {
                    viewModel.setPokemon(it.data ?: pokemonData)
                    binding.shimmerDescription.isVisible = false
                    binding.tvDescription.isVisible = true
                }
            }
        }
    }
}