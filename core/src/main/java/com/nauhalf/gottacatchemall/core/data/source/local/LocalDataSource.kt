package com.nauhalf.gottacatchemall.core.data.source.local

import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val pokemonDao: PokemonDao) {
    fun getAllPokemon(): Flow<List<PokemonAllStuffEntity>> = pokemonDao.getAllPokemon()
    fun getFavoritePokemon(): Flow<List<PokemonAllStuffEntity>> = pokemonDao.getFavoritePokemon()

    suspend fun insertPokemon(pokemons: List<PokemonAllStuffEntity>) = pokemonDao.insertPokemonStuff(pokemons)

    suspend fun setFavoritePokemon(pokemon: PokemonEntity, newState: Boolean) {
        pokemon.isFavorite = newState
        pokemonDao.updateFavoritePokemon(pokemon)
    }
}