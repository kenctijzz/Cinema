package com.example.cinema.ui.navigation

import com.example.cinema.domain.model.Film
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object FilmList

    @Serializable
    data object ActorList

    @Serializable
    data class ActorDetail(val id: Int)

    @Serializable
    data object FavoriteFilms


    @Serializable
    data class FilmDetail(val id: Int)
}