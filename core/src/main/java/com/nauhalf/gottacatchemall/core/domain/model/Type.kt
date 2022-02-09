package com.nauhalf.gottacatchemall.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Type(
    val slot: Int,
    val type: String
) : Parcelable