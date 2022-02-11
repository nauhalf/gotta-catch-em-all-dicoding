package com.nauhalf.gottacatchemall.core.utils

import com.nauhalf.gottacatchemall.core.R
import com.nauhalf.gottacatchemall.core.domain.model.Type

fun String.idFromUrl(): Int{
    return this.substringAfter("pokemon").replace("/", "").toInt()
}

fun List<Type>.colorOfType(): Int {
    return when(this.firstOrNull()?.type){
        "normal" -> R.attr.colorNormal
        "fighting" -> R.attr.colorFighting
        "flying" -> R.attr.colorFlying
        "poison" -> R.attr.colorPoison
        "ground" -> R.attr.colorGround
        "rock" -> R.attr.colorRock
        "bug" -> R.attr.colorBug
        "ghost" -> R.attr.colorGhost
        "steel" -> R.attr.colorSteel
        "fire" -> R.attr.colorFire
        "water" -> R.attr.colorWater
        "grass" -> R.attr.colorGrass
        "electric" -> R.attr.colorElectric
        "psychic" -> R.attr.colorPhysic
        "ice" -> R.attr.colorIce
        "dragon" -> R.attr.colorDragon
        "dark" -> R.attr.colorDark
        "fairy" -> R.attr.colorFairy
        else -> R.attr.colorDarkGray
    }
}