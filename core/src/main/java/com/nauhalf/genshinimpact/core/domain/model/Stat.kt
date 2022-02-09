package com.nauhalf.genshinimpact.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stat(
    val baseStat: Int,
    val stat: String
) : Parcelable