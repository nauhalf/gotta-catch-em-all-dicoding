package com.nauhalf.gottacatchemall.core.data.source.local

import androidx.paging.PagingSource
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.room.PokemonDao
import com.nauhalf.gottacatchemall.core.data.source.local.room.PokemonDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(val database: PokemonDatabase, private val pokemonDao: PokemonDao) {
    fun getAllPokemon(): Flow<List<PokemonAllStuffEntity>> = pokemonDao.getAllPokemon()

    fun getPagingPokemon(): PagingSource<Int, PokemonAllStuffEntity> = pokemonDao.getPagingPokemon()

    fun getFavoritePokemon(): Flow<List<PokemonAllStuffEntity>> = pokemonDao.getFavoritePokemon()

    suspend fun insertPokemon(pokemons: List<PokemonAllStuffEntity>) = pokemonDao.insertPokemonStuff(pokemons)

    suspend fun setFavoritePokemon(pokemon: PokemonEntity, newState: Boolean) {
        pokemon.isFavorite = newState
        pokemonDao.updateFavoritePokemon(pokemon)
    }
}