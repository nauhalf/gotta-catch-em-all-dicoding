package com.nauhalf.gottacatchemall.ui.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.usecase.PokemonUseCase
import com.nauhalf.gottacatchemall.core.utils.UiAction
import com.nauhalf.gottacatchemall.core.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase) : ViewModel() {
//    val pokemon = pokemonUseCase.getAllPokemon().asLiveData()
    val state :StateFlow<UiState>
    val pagingDataFlow: Flow<PagingData<Pokemon>>
    /**
     * Processor of side effects from the UI which in turn feedback into [state]
     */
    val accept: (UiAction) -> Unit
    init {
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val searches = actionStateFlow
            .distinctUntilChanged()
            .onStart { emit(UiAction.Search) }

        pagingDataFlow = searches
            .flatMapLatest { loadData() }
            .cachedIn(viewModelScope)

        state = searches.map {
            UiState(
                hasNotScrolledForCurrentSearch = true
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = UiState()
        )
        accept = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }

    }

    fun loadData(): Flow<PagingData<Pokemon>> = pokemonUseCase.getPagingPokemon()
}