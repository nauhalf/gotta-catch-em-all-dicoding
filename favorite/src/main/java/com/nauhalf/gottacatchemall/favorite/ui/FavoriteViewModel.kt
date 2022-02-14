package com.nauhalf.gottacatchemall.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.usecase.PokemonUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(pokemonUseCase: PokemonUseCase) : ViewModel() {
    val pokemons = pokemonUseCase.getFavoritePokemon().asLiveData()
    private val _filteredPokemon = MutableLiveData<List<Pokemon>>()
    val filteredPokemon: LiveData<List<Pokemon>> = _filteredPokemon
    val keyword = MutableLiveData("")


    fun onSearchChanged(value: String?) {
        if (value.isNullOrEmpty()) {
            _filteredPokemon.value = pokemons.value
            return
        }

        _filteredPokemon.value = pokemons.value?.filter {
            it.name.contains(value, true)
        }
    }
}