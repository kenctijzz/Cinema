package com.example.cinema.data.remote.films.dto

data class FilmResponse(
    val results: List<FilmModel>
)

data class FilmVideosResponse(
    val results: List<FilmVideoModel>?
)

data class FilmPhotosResponse(
    val backdrops: List<FilmPhotoModel>?
)