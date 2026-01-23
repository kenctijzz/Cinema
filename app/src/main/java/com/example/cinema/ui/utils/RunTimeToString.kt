package com.example.cinema.ui.utils

fun Int.runTimeToString(): String = ("${this/60}h ${this % 60}min")