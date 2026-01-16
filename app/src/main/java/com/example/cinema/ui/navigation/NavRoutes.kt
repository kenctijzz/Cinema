package com.example.cinema.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object FilmList

    @Serializable
    data class FilmDetail(val id: Int)
}