package com.example.cinema.ui.utils

fun Int.runTimeToString(): String = ("${this/60}ч ${this % 60}мин")