package com.nauhalf.gottacatchemall.ui.detail

import androidx.lifecycle.*
import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase) :
    ViewModel() {
    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon
    val pokemonSpecies = MutableLiveData<Resource<Pokemon>>()

    fun setPokemon(value: Pokemon) {
        _pokemon.value = value
    }

    fun getPokemonSpecies() = viewModelScope.launch {
        pokemonUseCase.getPokemonSpecies(_pokemon.value as Pokemon).collect {
            pokemonSpecies.value = it
        }
    }

    fun setFavorite() = flow {
        _pokemon.value?.let {
            emit(pokemonUseCase.setFavoritePokemon(it, !it.isFavorite).first())
        }
    }
}