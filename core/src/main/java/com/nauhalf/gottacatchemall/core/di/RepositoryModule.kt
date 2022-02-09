package com.nauhalf.gottacatchemall.core.di

import com.nauhalf.gottacatchemall.core.data.source.PokemonRepository
import com.nauhalf.gottacatchemall.core.domain.repository.IPokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(pokemonRepository: PokemonRepository): IPokemonRepository
}