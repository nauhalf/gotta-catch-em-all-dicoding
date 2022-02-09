package com.nauhalf.gottacatchemall.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("types")
    val types: List<TypeResponse>,

    @SerializedName("stats")
    val stats: List<StatResponse>,

    @SerializedName("sprites")
    val sprites: Sprites? = null
) : Parcelable {
    @Parcelize
    data class Sprites(
        @SerializedName("other")
        val other: Other? = null,
    ) : Parcelable {

        @Parcelize
        data class Other(
            @SerializedName("dream_world")
            val dreamWorld: DreamWorld? = null,

            @SerializedName("official-artwork")
            var officialArtwork: OfficialArtwork? = null
        ) : Parcelable {

            @Parcelize
            data class DreamWorld(
                @SerializedName("front_default")
                val frontDefault: String?
            ) : Parcelable

            @Parcelize
            data class OfficialArtwork(
                @SerializedName("front_default")
                val frontDefault: String?
            ) : Parcelable
        }

    }
}