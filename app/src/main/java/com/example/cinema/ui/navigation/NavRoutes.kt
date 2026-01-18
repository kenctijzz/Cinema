package com.example.cinema.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object FilmList
    @Serializable
    object FavoriteFilms
    @Serializable
    data class FilmDetail(val id: Int)
}