package com.nauhalf.gottacatchemall.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.nauhalf.gottacatchemall.core.utils.idFromUrl
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonPagingResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
) : Parcelable {
    fun getId() = url.idFromUrl()
}