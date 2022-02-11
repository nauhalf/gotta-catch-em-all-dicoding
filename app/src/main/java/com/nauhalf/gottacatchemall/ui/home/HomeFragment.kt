package com.nauhalf.gottacatchemall.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.nauhalf.gottacatchemall.R
import com.nauhalf.gottacatchemall.adapter.PokemonGridItemDecoration
import com.nauhalf.gottacatchemall.adapter.PokemonListAdapter
import com.nauhalf.gottacatchemall.core.base.BaseFragment
import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.databinding.FragmentHomeBinding
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
        binding.apply {

        }
    }

    private fun observeLiveData() {
        viewModel.pokemon.observe(viewLifecycleOwner){ pokemons ->
            when(pokemons){
                is Resource.Error -> {}
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    pokemonListAdapter.submitList(pokemons.data)
                }
            }

        }
    }
}