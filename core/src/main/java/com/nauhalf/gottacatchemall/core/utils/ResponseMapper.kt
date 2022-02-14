package com.nauhalf.gottacatchemall.core.utils

import android.util.Log
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.TypeEntity
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.response.StatResponse
import com.nauhalf.gottacatchemall.core.data.source.remote.response.TypeResponse

fun List<PokemonResponse>.toPokemonAllStuffEntities(): List<PokemonAllStuffEntity> {
    val pokemonList = mutableListOf<PokemonAllStuffEntity>()
    val entities = this.map {
        PokemonAllStuffEntity(
            pokemon = PokemonEntity(
                id = it.id,
                name = it.name.extractPokemonName(),
                height = it.height,
                weight = it.weight,
                isFavorite = false,
                imageUrl = it.sprites?.other?.officialArtwork?.frontDefault
                    ?: it.sprites?.other?.dreamWorld?.frontDefault,
                description = null,
                captureRate = null
            ),
            stats = it.stats.toStatEntities(it.id),
            types = it.types.toTypeEntities(it.id),
        )
    }
    pokemonList.addAll(entities)
    return pokemonList
}

fun List<StatResponse>.toStatEntities(id: Int): List<StatEntity> {
    return this.map {
        StatEntity(
            pokemonId = id,
            baseStat = it.baseStat,
            stat = it.stat.name,
        )
    }
}

fun List<TypeResponse>.toTypeEntities(id: Int): List<TypeEntity> {
    return this.map {
        TypeEntity(
            pokemonId = id,
            slot = it.slot,
            type = it.type.name,
        )
    }
}