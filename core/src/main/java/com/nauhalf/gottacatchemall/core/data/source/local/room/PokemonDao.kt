package com.nauhalf.gottacatchemall.core.data.source.local.room

import androidx.room.*
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.TypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PokemonDao {
    @Query("SELECT * FROM pokemon INNER JOIN stat ON pokemon._id = stat.pokemonId INNER JOIN type ON pokemon._id = type.pokemonId")
    abstract fun getAllPokemon(): Flow<List<PokemonAllStuffEntity>>

    @Query("SELECT * FROM pokemon INNER JOIN stat ON pokemon._id = stat.pokemonId INNER JOIN type ON pokemon._id = type.pokemonId WHERE pokemon.isFavorite = 1")
    abstract fun getFavoritePokemon(): Flow<List<PokemonAllStuffEntity>>

    @Transaction
    open suspend fun insertPokemonStuff(pokemons: List<PokemonAllStuffEntity>){
        pokemons.forEach {
            insertPokemon(it.pokemon)
            insertStat(it.stats)
            insertType(it.types)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPokemon(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertStat(pokemon: List<StatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertType(pokemon: List<TypeEntity>)

    @Update
    abstract suspend fun updateFavoritePokemon(pokemon: PokemonEntity)
}