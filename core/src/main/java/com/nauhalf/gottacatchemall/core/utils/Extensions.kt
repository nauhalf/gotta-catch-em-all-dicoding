package com.nauhalf.gottacatchemall.core.utils

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.nauhalf.gottacatchemall.core.R
import com.nauhalf.gottacatchemall.core.domain.model.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun String.idFromUrl(): Int {
    return this.substringAfter("pokemon").replace("/", "").toInt()
}

fun List<Type>.colorOfFirstType(): Int {
    return this.firstOrNull().colorOfType()
}

fun Type?.colorOfType(): Int {
    return when (this?.type) {
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

fun String.mapStat(): String {
    return when (this) {
        "attack" -> "ATK"
        "defense" -> "DEF"
        "hp" -> "HP"
        "special-attack" -> "SP-ATK"
        "special-defense" -> "SP-DEF"
        "speed" -> "SPD"
        else -> this
    }
}

fun String.extractPokemonName(): String {
    val words = this.split("-")
    return words.joinToString("-") {
        it.lowercase().replaceFirstChar { firstChar -> firstChar.uppercase() }
    }
}

fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    message?.let {
        Toast.makeText(this, it, duration).show()
    }
}

fun Int.toRealSizePokemon(): Double {
    return this.toDouble() / 10
}

fun Context.startIntent(clazz: Class<*>, extras: (Intent) -> Unit) {
    val intent = Intent(this, clazz)
    extras.invoke(intent)
    startActivity(intent)
}

fun <T> debounce(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return {
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(waitMs)
            destinationFunction.invoke(it)
        }
    }
}

fun EditText.onTextChanged(listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}