package com.nauhalf.gottacatchemall.core.data.source

import android.util.Log
import androidx.paging.*
import com.nauhalf.gottacatchemall.core.data.source.local.LocalDataSource
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.data.source.remote.RemoteDataSource
import com.nauhalf.gottacatchemall.core.data.source.remote.network.ApiResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonResponse
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.repository.IPokemonRepository
import com.nauhalf.gottacatchemall.core.utils.toPokemonAllStuffEntities
import com.nauhalf.gottacatchemall.core.utils.toPokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.utils.toPokemonDomain
import com.nauhalf.gottacatchemall.core.utils.toPokemonDomains
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IPokemonRepository {
    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, List<PokemonResponse>>() {
            override fun loadFromDb(): Flow<List<Pokemon>> {
                return localDataSource.getAllPokemon().map {
                    it.toPokemonDomains()
                }
            }

            override fun shouldFetch(data: List<Pokemon>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<PokemonResponse>>> {
                return remoteDataSource.getAllPokemon()
            }

            override suspend fun saveCallResult(data: List<PokemonResponse>) {
                val pokemonList = data.toPokemonAllStuffEntities()
                localDataSource.insertPokemon(pokemonList)
            }

        }.asFlow()

    override fun getFavoritePokemon(): Flow<List<Pokemon>> {
        return localDataSource.getFavoritePokemon().map {
            it.toPokemonDomains()
        }
    }

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavoritePokemon(
                pokemon = pokemon.toPokemonAllStuffEntity().pokemon,
                state
            )
        }
    }

    override fun getPagingPokemon(): Flow<PagingData<Pokemon>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = POKEMON_STARTING_LIMIT, enablePlaceholders = false),
            remoteMediator = PokemonRemoteMediator(
                localDataSource.database,
                remoteDataSource
            ),
            pagingSourceFactory = { localDataSource.getPagingPokemon() }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toPokemonDomain()
            }
        }
    }


}