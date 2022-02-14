package com.nauhalf.gottacatchemall.core.data.source.remote

import android.util.Log
import com.nauhalf.gottacatchemall.core.data.source.remote.network.ApiResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.network.PokeApi
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonSpeciesResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val pokeApi: PokeApi) {
    suspend fun getAllPokemon(
        limit: Int = 151,
        offset: Int = 0
    ): Flow<ApiResponse<List<PokemonResponse>>> = flow {
        coroutineScope {

            try {
                val response = pokeApi.getAllPokemon(limit, offset)
                val dataArray = response.pokemons
                val newArray = withContext(Dispatchers.IO) {
                    dataArray.map {
                        getDetailPokemonAsync(it.getId())
                    }.awaitAll()
                }
                if (newArray.isNotEmpty()) {
                    emit(ApiResponse.Success(newArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPokemonSpecies(id: Int): Flow<ApiResponse<PokemonSpeciesResponse>> = flow {
        try {
            val response = pokeApi.getPokemonSpecies(id)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    private fun CoroutineScope.getDetailPokemonAsync(id: Int): Deferred<PokemonResponse> =
        async(Dispatchers.IO) {
            return@async pokeApi.getPokemon(id)
        }
}