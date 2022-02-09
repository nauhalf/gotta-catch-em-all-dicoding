package com.nauhalf.genshinimpact.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Type(
    val slot: Int,
    val type: String
) : Parcelable