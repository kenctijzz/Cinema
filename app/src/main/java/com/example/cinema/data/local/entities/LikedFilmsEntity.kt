package com.example.cinema.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "liked_films")
data class LikedFilmsEntity(
    @PrimaryKey val id: Int,
    val isFavorite: Boolean,
    val page: Int,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val overview: String?,
    val title: String,
    val adult: Boolean,
    @SerializedName("vote_average")
    val rating: Double,
    val popularity: Double,
    @SerializedName("original_language")
    val language: String?,
    @SerializedName("runtime")
    val runtime: String?,
    val video: String?,
    val photos: List<String>,
    val userRating: Int?
)