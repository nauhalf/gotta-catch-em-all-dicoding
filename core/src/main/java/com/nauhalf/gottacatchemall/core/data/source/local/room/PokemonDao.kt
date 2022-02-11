package com.nauhalf.gottacatchemall.core.data.source.local.room

import androidx.room.*
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.TypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PokemonDao {
    @Query("SELECT * FROM pokemon")
    abstract fun getAllPokemon(): Flow<List<PokemonAllStuffEntity>>


    @Query("SELECT * FROM pokemon")
    abstract fun getFavoritePokemon(): Flow<List<PokemonAllStuffEntity>>

    @Transaction
    open suspend fun insertPokemonStuff(pokemons: List<PokemonAllStuffEntity>){
        pokemons.forEach {
            insertPokemon(it.pokemon)
            deleteStat(it.pokemon.id)
            insertStat(it.stats)
            deleteType(it.pokemon.id)
            insertType(it.types)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertPokemon(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertStat(pokemon: List<StatEntity>)

    @Query("DELETE FROM stat WHERE pokemonId=:pokemonId")
    abstract suspend fun deleteStat(pokemonId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertType(pokemon: List<TypeEntity>)

    @Query("DELETE FROM type WHERE pokemonId=:pokemonId")
    abstract suspend fun deleteType(pokemonId: Int)

    @Update
    abstract suspend fun updateFavoritePokemon(pokemon: PokemonEntity)
}