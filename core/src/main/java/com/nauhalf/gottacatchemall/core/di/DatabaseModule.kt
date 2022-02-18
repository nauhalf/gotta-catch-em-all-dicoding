package com.nauhalf.gottacatchemall.core.di

import android.content.Context
import androidx.room.Room
import com.nauhalf.gottacatchemall.core.data.source.local.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("pokedex".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            PokemonDatabase::class.java,
            "Pokemon.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }


    @Provides
    fun providePokemonDao(database: PokemonDatabase) = database.pokemonDao()
}