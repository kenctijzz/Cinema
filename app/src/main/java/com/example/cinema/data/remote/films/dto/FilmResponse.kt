package com.example.cinema.data.remote.films.dto

data class FilmResponse(
    val results: List<FilmModel>
)

data class FilmVideosResponse(
    val results: List<FilmVideoModel>?
)