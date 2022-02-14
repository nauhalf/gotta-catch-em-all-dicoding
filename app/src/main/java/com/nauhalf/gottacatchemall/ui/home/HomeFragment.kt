package com.nauhalf.gottacatchemall.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.nauhalf.gottacatchemall.R
import com.nauhalf.gottacatchemall.adapter.PokemonGridItemDecoration
import com.nauhalf.gottacatchemall.adapter.PokemonListAdapter
import com.nauhalf.gottacatchemall.core.base.BaseFragment
import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.utils.debounce
import com.nauhalf.gottacatchemall.core.utils.onTextChanged
import com.nauhalf.gottacatchemall.core.utils.startIntent
import com.nauhalf.gottacatchemall.databinding.FragmentHomeBinding
import com.nauhalf.gottacatchemall.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var pokemonListAdapter: PokemonListAdapter

    override fun baseOnCreateView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        observeLiveData()
    }

    private fun setUp() {
        pokemonListAdapter = PokemonListAdapter(object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }

        }, onItemSelected = { pokemon ->
            requireContext().startIntent(DetailActivity::class.java) {
                it.putExtra(DetailActivity.EXTRA_DATA, pokemon)
            }

        })
        binding.apply {
            rvPokemon.apply {
                adapter = pokemonListAdapter
                addItemDecoration(
                    PokemonGridItemDecoration(
                        resources.getDimensionPixelSize(R.dimen.xsmall),
                        3
                    )
                )
            }

            val searchChanged: (String) -> Unit = debounce(
                300L,
                viewLifecycleOwner.lifecycleScope,
                viewModel::onSearchChanged
            )
            etFilter.onTextChanged(searchChanged)
        }
    }

    private fun observeLiveData() {
        viewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            if (pokemon != null) {
                when (pokemon) {
                    is Resource.Error -> {
                        stopLoading()
                    }
                    is Resource.Loading -> {
                        startLoading()
                    }
                    is Resource.Success -> {
                        viewModel.tempPokemon = pokemon.data ?: listOf()
                        viewModel.onSearchChanged(viewModel.keyword.value)
                        stopLoading()
                    }
                }
            }
        }

        viewModel.filteredPokemon.observe(viewLifecycleOwner) { pokemons ->
            pokemonListAdapter.submitList(pokemons)
        }
    }


    private fun stopLoading() {
        binding.lottieLoading.progress = 0f
        binding.lottieLoading.pauseAnimation()
        binding.lottieLoading.visibility = View.GONE
    }

    private fun startLoading() {
        binding.lottieLoading.playAnimation()
        binding.lottieLoading.visibility = View.VISIBLE
    }
}