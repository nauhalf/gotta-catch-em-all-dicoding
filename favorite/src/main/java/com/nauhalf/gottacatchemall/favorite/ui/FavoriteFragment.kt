package com.nauhalf.gottacatchemall.favorite.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.nauhalf.gottacatchemall.adapter.PokemonGridItemDecoration
import com.nauhalf.gottacatchemall.adapter.PokemonListAdapter
import com.nauhalf.gottacatchemall.core.base.BaseFragment
import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.utils.debounce
import com.nauhalf.gottacatchemall.core.utils.onTextChanged
import com.nauhalf.gottacatchemall.core.utils.startIntent
import com.nauhalf.gottacatchemall.di.FavoriteModuleDependencies
import com.nauhalf.gottacatchemall.favorite.DaggerFavoriteComponent
import com.nauhalf.gottacatchemall.favorite.R
import com.nauhalf.gottacatchemall.favorite.databinding.FragmentFavoriteBinding
import com.nauhalf.gottacatchemall.favorite.factory.ViewModelFactory
import com.nauhalf.gottacatchemall.ui.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by viewModels<FavoriteViewModel> {
        factory
    }

    private lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoriteComponent
            .builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun baseOnCreateView() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.pokemons.observe(viewLifecycleOwner) {
            viewModel.onSearchChanged(viewModel.keyword.value)
        }

        viewModel.filteredPokemon.observe(viewLifecycleOwner) { pokemons ->
            pokemonListAdapter.submitList(pokemons)
        }
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
                        resources.getDimensionPixelSize(com.nauhalf.gottacatchemall.R.dimen.xsmall),
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

}