package com.nauhalf.gottacatchemall.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatResponse(
    @SerializedName("baseStat")
    val baseStat: Int,
    @SerializedName("stat")
    val stat: Stat
) : Parcelable {
    @Parcelize
    data class Stat(
        @SerializedName("name")
        var name: String
    ) : Parcelable
}