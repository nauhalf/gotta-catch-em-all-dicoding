package com.nauhalf.gottacatchemall.core.data.source

import com.nauhalf.gottacatchemall.core.data.source.local.LocalDataSource
import com.nauhalf.gottacatchemall.core.data.source.remote.RemoteDataSource
import com.nauhalf.gottacatchemall.core.data.source.remote.network.ApiResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonSpeciesResponse
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.repository.IPokemonRepository
import com.nauhalf.gottacatchemall.core.utils.toPokemonAllStuffEntities
import com.nauhalf.gottacatchemall.core.utils.toPokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.utils.toPokemonDomain
import com.nauhalf.gottacatchemall.core.utils.toPokemonDomains
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean): Flow<Pokemon> = flow {
        val result =
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                localDataSource.setFavoritePokemon(
                    pokemon = pokemon.toPokemonAllStuffEntity().pokemon,
                    state
                )

            }
        emitAll(result.map { it.toPokemonDomain() })
    }

    override fun getPokemonSpecies(pokemon: Pokemon): Flow<Resource<Pokemon>> =
        object : NetworkBoundResource<Pokemon, PokemonSpeciesResponse>() {
            override fun loadFromDb(): Flow<Pokemon> {
                return localDataSource.getPokemonById(pokemon.id).map { it.toPokemonDomain() }
            }

            override fun shouldFetch(data: Pokemon?): Boolean {
                return data?.description == null
            }

            override suspend fun createCall(): Flow<ApiResponse<PokemonSpeciesResponse>> {
                return remoteDataSource.getPokemonSpecies(pokemon.id)
            }

            override suspend fun saveCallResult(data: PokemonSpeciesResponse) {
                val p = pokemon.toPokemonAllStuffEntity()
                val description = data.flavorTextEntries.filter { it.language == "en" }.distinct()
                    .take(3).joinToString(" ") { it.flavorText }
                    .replace("[\\n\\t\\f]".toRegex(), " ")
                localDataSource.updateDescription(p.pokemon, description, data.captureRate)
            }

        }.asFlow()

}