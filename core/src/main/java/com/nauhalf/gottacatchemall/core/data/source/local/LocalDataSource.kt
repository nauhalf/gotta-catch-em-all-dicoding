package com.nauhalf.gottacatchemall.core.data.source.local

import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.room.PokemonDao
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val pokemonDao: PokemonDao) {
    fun getAllPokemon(): Flow<List<PokemonAllStuffEntity>> = pokemonDao.getAllPokemon()

    fun getPokemonById(pokemonId: Int): Flow<PokemonAllStuffEntity> =
        pokemonDao.getPokemonById(pokemonId)

    fun getFavoritePokemon(): Flow<List<PokemonAllStuffEntity>> = pokemonDao.getFavoritePokemon()

    suspend fun insertPokemon(pokemons: List<PokemonAllStuffEntity>) =
        pokemonDao.insertPokemonStuff(pokemons)

    suspend fun setFavoritePokemon(pokemon: PokemonEntity, newState: Boolean): Flow<PokemonEntity> =
        flow {
            pokemon.isFavorite = newState
            pokemonDao.updatePokemon(pokemon)
            emit(pokemon)
        }

    suspend fun updatePokemonRateDescription(
        pokemon: PokemonEntity,
        description: String?,
        captureRate: Int
    ) {
        pokemon.description = description
        pokemon.captureRate = captureRate
        pokemonDao.updatePokemon(pokemon)
    }
}