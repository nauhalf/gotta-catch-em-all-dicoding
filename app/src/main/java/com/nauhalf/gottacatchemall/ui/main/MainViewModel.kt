package com.nauhalf.gottacatchemall.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nauhalf.gottacatchemall.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(pokemonUseCase: PokemonUseCase) : ViewModel() {
    val pokemon = pokemonUseCase.getAllPokemon().asLiveData()
}