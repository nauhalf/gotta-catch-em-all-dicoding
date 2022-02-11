package com.nauhalf.gottacatchemall.core.data.source

import androidx.paging.*
import androidx.room.withTransaction
import com.nauhalf.gottacatchemall.core.data.source.local.LocalDataSource
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.RemoteKey
import com.nauhalf.gottacatchemall.core.data.source.local.room.PokemonDatabase
import com.nauhalf.gottacatchemall.core.data.source.remote.RemoteDataSource
import com.nauhalf.gottacatchemall.core.data.source.remote.network.ApiResponse
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.utils.toPokemonAllStuffEntities
import com.nauhalf.gottacatchemall.core.utils.toPokemonDomains
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val database: PokemonDatabase,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, PokemonAllStuffEntity>(){
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonAllStuffEntity>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: POKEMON_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
                }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = remoteDataSource.getAllPokemon(state.config.pageSize, page * state.config.pageSize)
            val repos = apiResponse.first()
            val list = mutableListOf<PokemonAllStuffEntity>()
            var endOfPaginationReached = false
            val nextKey = when (repos) {
                is ApiResponse.Success -> {
                    endOfPaginationReached = repos.data.isEmpty()
                    list.addAll(repos.data.toPokemonAllStuffEntities())
                    page + 1
                }
                is ApiResponse.Error -> {
                    return MediatorResult.Error(Exception(repos.errorMessage))
                }
                ApiResponse.Empty -> {
                    endOfPaginationReached = true
                    null
                }
            }
            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    database.remoteKeyDao().clearRemoteKeys()
                    database.pokemonDao().clearPokemon()
                }
                val prevKey = if(page == POKEMON_STARTING_PAGE_INDEX) null else page - 1
                val keys = list.map {
                    RemoteKey(pokemonId = it.pokemon.id, prevKey, nextKey)
                }
                list.forEach {
                    database.pokemonDao().insertPokemon(it.pokemon)
                    database.pokemonDao().deleteStat(it.pokemon.id)
                    database.pokemonDao().insertStat(it.stats)
                    database.pokemonDao().deleteType(it.pokemon.id)
                    database.pokemonDao().insertType(it.types)
                }
                database.remoteKeyDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException){
            return MediatorResult.Error(e)
        }catch (e: HttpException){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonAllStuffEntity>): RemoteKey? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.pokemon?.let { pokemon ->
                // Get the remote keys of the last item retrieved
                database.remoteKeyDao().remoteKeyPokemonId(pokemon.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonAllStuffEntity>): RemoteKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.pokemon?.let { pokemon ->
                // Get the remote keys of the first items retrieved
                database.remoteKeyDao().remoteKeyPokemonId(pokemon.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokemonAllStuffEntity>
    ): RemoteKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.pokemon?.id?.let { pokemonId ->
                database.remoteKeyDao().remoteKeyPokemonId(pokemonId)
            }
        }
    }
}