package com.nauhalf.gottacatchemall.core.data.source.local

import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    suspend fun setFavoritePokemon(
        pokemon: PokemonEntity,
        newState: Boolean
    ): Flow<PokemonAllStuffEntity> =
        flow {
            pokemon.isFavorite = newState
            pokemonDao.updatePokemonFavorite(pokemon.id, newState)

            val newPokemon = getPokemonById(pokemon.id).first()
            emit(
                newPokemon
            )
        }

    suspend fun updateDescription( pokemon: PokemonEntity,
                                   description: String?,
                                   captureRate: Int) {
        pokemonDao.updatePokemonDescription(pokemon.id, description, captureRate)
    }
}