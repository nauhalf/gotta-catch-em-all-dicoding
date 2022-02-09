package com.nauhalf.gottacatchemall.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    var id: Int,

    @ColumnInfo(name = "name")
    @NonNull
    val name: String,

    @ColumnInfo(name = "height")
    @NonNull
    val height: Int,

    @ColumnInfo(name = "weight")
    @NonNull
    val weight: Int,

    @ColumnInfo(name = "isFavorite")
    @NonNull
    var isFavorite: Boolean = false,

    @ColumnInfo(name= "imageUrl")
    var imageUrl: String? = null
) : Parcelable