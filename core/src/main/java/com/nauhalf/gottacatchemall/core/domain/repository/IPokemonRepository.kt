package com.nauhalf.gottacatchemall.core.domain.repository

import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>
    fun getFavoritePokemon(): Flow<List<Pokemon>>
    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean): Flow<Pokemon>
    fun getPokemonSpecies(pokemon: Pokemon): Flow<Resource<Pokemon>>
}