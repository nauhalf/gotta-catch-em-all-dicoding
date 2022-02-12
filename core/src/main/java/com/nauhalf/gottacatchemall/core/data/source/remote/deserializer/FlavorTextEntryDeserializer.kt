package com.nauhalf.gottacatchemall.core.data.source.remote.deserializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.nauhalf.gottacatchemall.core.data.source.remote.response.PokemonSpeciesResponse
import java.lang.reflect.Type

class FlavorTextEntryDeserializer : JsonDeserializer<PokemonSpeciesResponse.FlavorTextEntry> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PokemonSpeciesResponse.FlavorTextEntry {

        if(json?.isJsonObject == true){
            val language = json.asJsonObject.get("language")
            if(language != null && language.isJsonObject){
                val name = language.asJsonObject.get("name")
                if(name != null){
                    json.asJsonObject.add("language_name", name.asJsonPrimitive)
                }
            }
        }

        return Gson().fromJson(json, PokemonSpeciesResponse.FlavorTextEntry::class.java)


    }
}