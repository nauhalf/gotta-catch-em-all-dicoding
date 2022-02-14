package com.nauhalf.gottacatchemall.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nauhalf.gottacatchemall.R
import com.nauhalf.gottacatchemall.adapter.PokemonPagingAdapter
import com.nauhalf.gottacatchemall.adapter.ReposLoadStateAdapter
import com.nauhalf.gottacatchemall.core.base.BaseActivity
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.utils.RemotePresentationState
import com.nauhalf.gottacatchemall.core.utils.UiAction
import com.nauhalf.gottacatchemall.core.utils.UiState
import com.nauhalf.gottacatchemall.core.utils.asRemotePresentationState
import com.nauhalf.gottacatchemall.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var pokemonAdapter: PokemonPagingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
        observeLiveData()
    }

    private fun setUp() {

        binding.apply {
            bindState(
                uiState = viewModel.state,
                pagingData = viewModel.pagingDataFlow,
                uiActions = viewModel.accept
            )
        }
    }

    private fun observeLiveData() {
//        viewModel.pokemon.observe(this) { pokemon ->
//            if (pokemon != null) {
//                when(pokemon){
//                    is Resource.Error -> {
//                        stopLoading()
//                    }
//                    is Resource.Loading -> {
//                        startLoading()
//                    }
//                    is Resource.Success -> {
//                        stopLoading()
//                        pokemonListAdapter.submitList(pokemon.data)
//                    }
//                }
//            }
//        }
    }

    private fun ActivityMainBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Pokemon>>,
        uiActions: (UiAction) -> Unit
    ) {
        pokemonAdapter = PokemonPagingAdapter(object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return (oldItem.id == newItem.id && oldItem.name == newItem.name)
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }

        })

        val footerAdapter = ReposLoadStateAdapter {
            pokemonAdapter.retry()
        }
//        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        val layoutManager = GridLayoutManager(this@MainActivity, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if (position == pokemonAdapter.itemCount  && footerAdapter.itemCount > 0) {
                    3
                } else {
                    1
                }
            }
        }

        rvPokemon.layoutManager = layoutManager
        rvPokemon.adapter = pokemonAdapter

        bindList(
            footer = footerAdapter,
            pokemonAdapter = pokemonAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun ActivityMainBinding.bindList(
        footer: ReposLoadStateAdapter,
        pokemonAdapter: PokemonPagingAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Pokemon>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll)
            }
        })
        val notLoading = pokemonAdapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }
        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(pokemonAdapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) rvPokemon.scrollToPosition(0)
            }
        }
        lifecycleScope.launch {
            pokemonAdapter.loadStateFlow.collect { loadState ->
                rvPokemon.isVisible =  loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                if(loadState.mediator?.refresh is LoadState.Loading){
                    lottieLoading.isVisible = true
                    startLoading()
                } else {
                    lottieLoading.isVisible = false
                    stopLoading()
                }
            }
        }
    }

    private fun stopLoading() {
        binding.lottieLoading.progress = 0f
        binding.lottieLoading.pauseAnimation()
    }

    private fun startLoading() {
        binding.lottieLoading.playAnimation()
    }
}