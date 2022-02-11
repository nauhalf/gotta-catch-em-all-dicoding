package com.nauhalf.gottacatchemall.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nauhalf.gottacatchemall.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase) : ViewModel(){
    val pokemon = pokemonUseCase.getAllPokemon().asLiveData()
}