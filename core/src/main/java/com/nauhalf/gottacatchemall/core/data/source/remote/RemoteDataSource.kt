package com.nauhalf.gottacatchemall.core.data.source.remote

import android.util.Log
import com.nauhalf.gottacatchemall.core.data.source.remote.network.ApiResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.network.PokeApi
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val pokeApi: PokeApi) {
    suspend fun getAllPokemon(limit: Int = 50, offset: Int = 0): Flow<ApiResponse<List<PokemonResponse>>> = channelFlow {
        coroutineScope {

            try {
                val response = pokeApi.getAllPokemon(limit, offset)
                val dataArray = response.pokemons
                Log.d("Pokemons Data Array", dataArray.toString())
                val newArray = withContext(Dispatchers.IO) {
                    dataArray.map {
                        getDetailPokemon(it.getId())
                    }.awaitAll()
                }
                if (newArray.isNotEmpty()) {
                    send(ApiResponse.Success(newArray))
                } else {
                    send(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                send(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }
    }

    fun CoroutineScope.getDetailPokemon(id: Int): Deferred<PokemonResponse> =
        async(Dispatchers.IO) {
            return@async pokeApi.getPokemon(id)
        }
}