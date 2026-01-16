package com.example.cinema.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FilmModel(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val adult: Boolean,
    val overview: String?
)