package com.nauhalf.gottacatchemall.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import javax.annotation.Nonnull


@Parcelize
@Entity(tableName = "type", primaryKeys = ["pokemonId", "slot", "type"])
data class TypeEntity(
    @ColumnInfo(name = "pokemonId")
    @Nonnull
    var pokemonId: Int,

    @ColumnInfo(name = "slot")
    @Nonnull
    var slot: Int,

    @ColumnInfo(name = "type")
    @Nonnull
    var type: String

) : Parcelable