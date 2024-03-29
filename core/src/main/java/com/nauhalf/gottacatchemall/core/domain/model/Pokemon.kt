package com.nauhalf.gottacatchemall.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val description: String?,
    val captureRate: Int?,
    val types: List<Type>,
    val stats: List<Stat>,
    val isFavorite: Boolean,
    val imageUrl: String?
) : Parcelable