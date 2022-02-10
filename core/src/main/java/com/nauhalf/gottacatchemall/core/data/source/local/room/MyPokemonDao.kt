package com.nauhalf.gottacatchemall.core.data.source.local.room

import androidx.room.*
import com.nauhalf.gottacatchemall.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MyPokemonDao {
    @Transaction
    @Query("SELECT * FROM my_pokemon ORDER BY _id ASC")
    abstract fun getAllMyPokemon(): Flow<List<MyPokemonWithAllStuffEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMyPokemon(pokemon: MyPokemonEntity)

    @Delete
    abstract suspend fun deleteMyPokemon(pokemon: List<MyPokemonEntity>)
}