package com.nauhalf.gottacatchemall.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyPokemonWithAllStuffEntity(
    @Embedded
    val myPokemon: MyPokemonEntity,

    @Relation(
        entity = PokemonEntity::class,
        parentColumn = "pokemonId",
        entityColumn = "_id"
    )
    val pokemonAllStuffEntity: PokemonAllStuffEntity
) : Parcelable