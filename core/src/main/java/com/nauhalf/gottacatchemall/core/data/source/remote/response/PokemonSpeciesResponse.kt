package com.nauhalf.gottacatchemall.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonSpeciesResponse(
    @SerializedName("capture_rate")
    val captureRate: Int,

    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>

) : Parcelable {
    @Parcelize
    data class FlavorTextEntry(
        @SerializedName("flavor_text")
        val flavorText: String,

        @SerializedName("language_name")
        val language: String
    ) : Parcelable
}