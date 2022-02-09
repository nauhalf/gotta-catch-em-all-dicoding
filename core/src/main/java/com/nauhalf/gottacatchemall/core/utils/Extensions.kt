package com.nauhalf.gottacatchemall.core.utils

fun String.idFromUrl(): Int{
    return this.substringAfter("pokemon").replace("/", "").toInt()
}