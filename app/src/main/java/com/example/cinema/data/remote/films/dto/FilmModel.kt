package com.example.cinema.data.remote.films.dto

import com.google.gson.annotations.SerializedName

data class FilmModel(
    @SerializedName(value = "filmId", alternate = ["kinopoiskId"])
    val kinopoiskId: Int,
    @SerializedName("filmLength")
    val runtime: String,
    @SerializedName("ratingKinopoisk")
    val rating: Double,
    val nameRu: String,
    val popularity: Double,
    @SerializedName("original_language")
    val language: String,
    @SerializedName("posterUrlPreview")
    val posterUrl: String?,
    @SerializedName("year")
    val releaseDate: String?,
    val adult: Boolean,
    val description: String?
)

data class SearchResponse(
    val page: Int,
    @SerializedName("films")
    val results: List<FilmModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
data class FilmVideoModel(
    @SerializedName("key")
    val videoKey: String?,
    @SerializedName("type")
    val type: String?
)
data class FilmPhotoModel(
    @SerializedName("imageUrl")
    val photo: String?
)