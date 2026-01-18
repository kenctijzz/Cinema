package com.example.cinema.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object FilmList

    @Serializable
    data object FavoriteFilms

    @Serializable
    data class FilmDetail(val id: Int)
}