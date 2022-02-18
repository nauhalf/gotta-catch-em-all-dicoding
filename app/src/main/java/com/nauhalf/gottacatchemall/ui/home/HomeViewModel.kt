package com.nauhalf.gottacatchemall.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(pokemonUseCase: PokemonUseCase) : ViewModel() {
    val pokemon = pokemonUseCase.getAllPokemon().asLiveData()

    lateinit var tempPokemon: List<Pokemon>

    private val _filteredPokemon = MutableLiveData<List<Pokemon>>()

    val filteredPokemon: LiveData<List<Pokemon>> = _filteredPokemon

    val keyword = MutableLiveData("")

    private val _loadingLiveData = MutableLiveData(false)

    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData


    fun setLoading(value: Boolean) {
        _loadingLiveData.value = value
    }


    fun onSearchChanged(value: String?) {
        if (value.isNullOrEmpty()) {
            _filteredPokemon.value = tempPokemon
            return
        }

        _filteredPokemon.value = tempPokemon.filter {
            it.name.contains(value, true)
        }
    }
}