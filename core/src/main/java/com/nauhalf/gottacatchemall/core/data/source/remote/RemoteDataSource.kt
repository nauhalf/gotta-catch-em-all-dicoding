package com.nauhalf.gottacatchemall.core.data.source.remote

import android.util.Log
import com.nauhalf.gottacatchemall.core.data.source.remote.network.ApiResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.network.PokeApi
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val pokeApi: PokeApi) {
    suspend fun getAllPokemon(): Flow<ApiResponse<List<PokemonResponse>>> = flow {
        coroutineScope {

            try {
                val response = pokeApi.getAllPokemon()
                val dataArray = response.pokemons
                val newArray = withContext(Dispatchers.IO) {
                    dataArray.map {
                        getDetailPokemon(it.getId())
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
    }

    fun CoroutineScope.getDetailPokemon(id: Int): Deferred<PokemonResponse> =
        async(Dispatchers.IO) {
            return@async pokeApi.getPokemon(id)
        }
}