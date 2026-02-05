package com.example.cinema.data.remote.films.dto

import com.google.gson.annotations.SerializedName

data class FilmResponse(
    @SerializedName("films")
    val results: List<FilmModel>
)

data class FilmVideosResponse(
    val results: List<FilmVideoModel>?
)

data class FilmPhotosResponse(
    @SerializedName("items")
    val backdrops: List<FilmPhotoModel>?
)