package com.nauhalf.gottacatchemall.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "my_pokemon")
data class MyPokemonEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    var id: Int = -1,

    @ColumnInfo(name = "pokemonId")
    @NonNull
    var pokemonId: Int,

    @ColumnInfo(name = "nickName")
    var nickName: String? = null,

    @ColumnInfo(name = "capturedAt")
    @NonNull
    var capturedAt: Long
) : Parcelable