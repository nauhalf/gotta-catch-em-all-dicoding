package com.nauhalf.gottacatchemall.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "stat", primaryKeys = ["pokemonId", "stat", "baseStat"])
data class StatEntity(
    @ColumnInfo(name = "pokemonId")
    @NonNull
    var pokemonId: Int,

    @ColumnInfo(name = "baseStat")
    @NonNull
    var baseStat: Int,

    @ColumnInfo(name = "stat")
    @NonNull
    var stat: String
) : Parcelable