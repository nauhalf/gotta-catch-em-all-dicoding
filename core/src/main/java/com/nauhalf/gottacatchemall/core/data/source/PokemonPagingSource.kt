package com.nauhalf.gottacatchemall.core.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nauhalf.gottacatchemall.core.data.source.remote.RemoteDataSource
import com.nauhalf.gottacatchemall.core.data.source.remote.network.ApiResponse
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.utils.toPokemonAllStuffEntities
import com.nauhalf.gottacatchemall.core.utils.toPokemonDomains
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

const val POKEMON_STARTING_PAGE_INDEX = 0
const val POKEMON_STARTING_LIMIT = 10
class PokemonPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Pokemon>() {


    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val position = params.key ?: POKEMON_STARTING_PAGE_INDEX
        return try {
            val response = remoteDataSource.getAllPokemon(params.loadSize, position * params.loadSize)
            val repos = response.first()
            val list = mutableListOf<Pokemon>()
            val nextKey = when (repos) {
                is ApiResponse.Success -> {
                    list.addAll(repos.data.toPokemonAllStuffEntities().toPokemonDomains())
                    position + 1
                }
                is ApiResponse.Error -> {
                    return LoadResult.Error(Exception(repos.errorMessage))
                }
                ApiResponse.Empty -> {
                    null
                }
            }
            LoadResult.Page(
                data = list,
                prevKey = if(position == POKEMON_STARTING_PAGE_INDEX) null else position -1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

    }
}