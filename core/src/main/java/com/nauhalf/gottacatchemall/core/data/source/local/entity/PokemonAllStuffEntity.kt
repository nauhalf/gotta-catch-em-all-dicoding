package com.nauhalf.gottacatchemall.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "pokemon")
data class PokemonAllStuffEntity(
    @Embedded
    val pokemon: PokemonEntity,

    @Relation(
        parentColumn = "_id",
        entityColumn = "pokemonId"
    )
    val stats: List<StatEntity>,

    @Relation(
        parentColumn = "_id",
        entityColumn = "pokemonId"
    )
    val types: List<TypeEntity>,
) : Parcelable