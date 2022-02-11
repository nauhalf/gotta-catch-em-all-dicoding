package com.nauhalf.gottacatchemall.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.nauhalf.gottacatchemall.R
import com.nauhalf.gottacatchemall.adapter.PokemonGridItemDecoration
import com.nauhalf.gottacatchemall.adapter.PokemonListAdapter
import com.nauhalf.gottacatchemall.core.base.BaseActivity
import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var pokemonListAdapter: PokemonListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        })
        binding.apply {
            rvPokemon.apply {
                adapter = pokemonListAdapter
                addItemDecoration(PokemonGridItemDecoration(resources.getDimensionPixelSize(R.dimen.xsmall), 3))
            }
        }
    }

    private fun observeLiveData() {
        viewModel.pokemon.observe(this) { pokemon ->
            if (pokemon != null) {
                when(pokemon){
                    is Resource.Error -> {
                        stopLoading()
                    }
                    is Resource.Loading -> {
                        startLoading()
                    }
                    is Resource.Success -> {
                        stopLoading()
                        pokemonListAdapter.submitList(pokemon.data)
                    }
                }
            }
        }
    }

    private fun stopLoading(){
        binding.lottieLoading.progress = 0f
        binding.lottieLoading.pauseAnimation()
        binding.lottieLoading.visibility = View.GONE
    }

    private fun startLoading(){
        binding.lottieLoading.playAnimation()
        binding.lottieLoading.visibility = View.VISIBLE
    }
}