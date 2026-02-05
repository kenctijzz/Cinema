package com.example.cinema.ui.utils

fun String.runTimeToString(): String = ("${this.toInt()/60}ч ${this.toInt() % 60}мин")