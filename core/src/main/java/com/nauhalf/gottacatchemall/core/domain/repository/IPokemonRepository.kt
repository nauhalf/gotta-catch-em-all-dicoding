package com.nauhalf.gottacatchemall.core.domain.repository

import androidx.paging.PagingData
import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.model.Stat
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>
    fun getFavoritePokemon(): Flow<List<Pokemon>>
    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean)
    fun getPagingPokemon(): Flow<PagingData<Pokemon>>
}