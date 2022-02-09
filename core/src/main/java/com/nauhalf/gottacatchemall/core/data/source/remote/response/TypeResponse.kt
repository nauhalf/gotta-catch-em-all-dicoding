package com.nauhalf.gottacatchemall.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeResponse(
    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val type: Type
) : Parcelable {

    @Parcelize
    data class Type(
        @SerializedName("name")
        val name: String
    ) : Parcelable
}