package com.nauhalf.gottacatchemall.core.domain.usecase

import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>
    fun getFavoritePokemon(): Flow<List<Pokemon>>
    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) : Flow<Boolean>
    fun getPokemonSpecies(pokemon: Pokemon) : Flow<Resource<Pokemon>>

}