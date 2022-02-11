package com.nauhalf.gottacatchemall.core.data.source.remote.response

import com.google.gson.annotations.SerializedName


data class ListPokemonPaginResponse(
    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val pokemons: List<PokemonPagingResponse>
)