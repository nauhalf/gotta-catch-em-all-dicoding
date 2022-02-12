package com.nauhalf.gottacatchemall.core.utils

import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.TypeEntity
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.model.Stat
import com.nauhalf.gottacatchemall.core.domain.model.Type


fun List<PokemonAllStuffEntity>.toPokemonDomains(): List<Pokemon> {
    return this.map {
        it.toPokemonDomain()
    }
}

fun PokemonAllStuffEntity.toPokemonDomain(): Pokemon {
    return Pokemon(
        id = this.pokemon.id,
        name = this.pokemon.name,
        height = this.pokemon.height,
        weight = this.pokemon.weight,
        description = this.pokemon.description,
        captureRate = this.pokemon.captureRate,
        stats = this.stats.toStatDomains(),
        types = this.types.toTypeDomains(),
        isFavorite = this.pokemon.isFavorite,
        imageUrl = this.pokemon.imageUrl
    )
}

fun List<StatEntity>.toStatDomains(): List<Stat> {
    return this.map {
        Stat(
            baseStat = it.baseStat,
            stat = it.stat
        )
    }
}

fun List<TypeEntity>.toTypeDomains(): List<Type> {
    return this.map {
        Type(
            slot = it.slot,
            type = it.type
        )
    }
}
