package com.nauhalf.gottacatchemall.core.domain.usecase

import androidx.paging.PagingData
import com.nauhalf.gottacatchemall.core.data.source.Resource
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonInteractor @Inject constructor(private val pokemonRepository: IPokemonRepository): PokemonUseCase{
    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> {
        return pokemonRepository.getAllPokemon()
    }

    override fun getFavoritePokemon(): Flow<List<Pokemon>> {
        return pokemonRepository.getFavoritePokemon()
    }

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) {
        pokemonRepository.setFavoritePokemon(pokemon, state)
    }

    override fun getPagingPokemon(): Flow<PagingData<Pokemon>> {
        return pokemonRepository.getPagingPokemon()
    }
}