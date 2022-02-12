package com.nauhalf.gottacatchemall.core.utils

import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.TypeEntity
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.model.Stat
import com.nauhalf.gottacatchemall.core.domain.model.Type


fun Pokemon.toPokemonAllStuffEntity(): PokemonAllStuffEntity {
    return PokemonAllStuffEntity(
        pokemon = PokemonEntity(
            id = this.id,
            name = this.name,
            height = this.height,
            weight = this.weight,
            description = this.description,
            isFavorite = this.isFavorite,
            imageUrl = this.imageUrl,
            captureRate = this.captureRate
        ),
        stats = this.stats.toStatEntities(this.id),
        types = this.types.toTypeEntities(this.id),
    )
}

fun List<Stat>.toStatEntities(id: Int): List<StatEntity> {
    return this.map {
        StatEntity(
            pokemonId = id,
            baseStat = it.baseStat,
            stat = it.stat
        )
    }
}

fun List<Type>.toTypeEntities(id: Int): List<TypeEntity> {
    return this.map {
        TypeEntity(
            pokemonId = id,
            slot = it.slot,
            type = it.type
        )
    }
}