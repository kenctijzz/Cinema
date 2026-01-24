package com.example.cinema.data.remote.films.dto

import com.google.gson.annotations.SerializedName

data class FilmModel(
    val id: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("vote_average")
    val rating: Double,
    val title: String,
    val popularity: Double,
    @SerializedName("original_language")
    val language: String,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val adult: Boolean,
    val overview: String?
)

data class FilmVideoModel(
    @SerializedName("key")
    val videoKey: String?,
    @SerializedName("type")
    val type: String?
)
data class FilmPhotoModel(
    @SerializedName("file_path")
    val photo: String?
)