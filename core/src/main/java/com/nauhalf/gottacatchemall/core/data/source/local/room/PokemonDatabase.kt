package com.nauhalf.gottacatchemall.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nauhalf.gottacatchemall.core.data.source.local.entity.*

@Database(entities = [PokemonEntity::class, StatEntity::class, TypeEntity::class, MyPokemonEntity::class], version = 3, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase(){
    abstract fun pokemonDao(): PokemonDao
    abstract fun myPokemonDao(): MyPokemonDao
}