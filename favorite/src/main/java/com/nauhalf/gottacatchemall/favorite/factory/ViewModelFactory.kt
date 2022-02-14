package com.nauhalf.gottacatchemall.favorite.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nauhalf.gottacatchemall.core.domain.usecase.PokemonUseCase
import com.nauhalf.gottacatchemall.favorite.ui.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val pokemonUseCase: PokemonUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                pokemonUseCase
            ) as T
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}