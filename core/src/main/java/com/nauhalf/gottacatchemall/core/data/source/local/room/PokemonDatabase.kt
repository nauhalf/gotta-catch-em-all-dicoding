package com.nauhalf.gottacatchemall.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nauhalf.gottacatchemall.core.data.source.local.entity.MyPokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.TypeEntity

@Database(
    entities = [PokemonEntity::class, StatEntity::class, TypeEntity::class, MyPokemonEntity::class],
    version = 4,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun myPokemonDao(): MyPokemonDao
}