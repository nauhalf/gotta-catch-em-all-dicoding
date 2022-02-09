package com.nauhalf.gottacatchemall.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import javax.annotation.Nonnull


@Parcelize
@Entity(tableName = "type")
data class TypeEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    var id: Int = -1,

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