package com.nauhalf.gottacatchemall.core.utils

import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonAllStuffEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.PokemonEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.StatEntity
import com.nauhalf.gottacatchemall.core.data.source.local.entity.TypeEntity
import com.nauhalf.gottacatchemall.core.domain.model.Pokemon
import com.nauhalf.gottacatchemall.core.domain.model.Stat
import com.nauhalf.gottacatchemall.core.domain.model.Type


fun List<PokemonAllStuffEntity>.toPokemonDomains(): List<Pokemon>{
    return this.map {
        Pokemon(
            id = it.pokemon.id,
            name = it.pokemon.name,
            height = it.pokemon.height,
            weight = it.pokemon.weight,
            stats = it.stats.toStatDomains(),
            types = it.types.toTypeDomains(),
            isFavorite = it.pokemon.isFavorite,
            imageUrl = it.pokemon.imageUrl
        )
    }
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
