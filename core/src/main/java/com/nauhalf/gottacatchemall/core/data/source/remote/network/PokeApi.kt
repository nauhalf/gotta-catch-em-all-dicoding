package com.nauhalf.gottacatchemall.core.data.source.remote.network

import com.nauhalf.gottacatchemall.core.data.source.remote.response.ListPokemonPaginResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getAllPokemon(@Query("limit") limit: Int, @Query("offset") offset: Int): ListPokemonPaginResponse

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse
}