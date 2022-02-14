package com.nauhalf.gottacatchemall.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nauhalf.gottacatchemall.core.data.source.remote.deserializer.FlavorTextEntryDeserializer
import com.nauhalf.gottacatchemall.core.data.source.remote.network.PokeApi
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonSpeciesResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                PokemonSpeciesResponse.FlavorTextEntry::class.java,
                FlavorTextEntryDeserializer()
            )
            .create()
    }

    @Provides
    fun providePokeApi(): PokeApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(PokeApi::class.java)
    }
}