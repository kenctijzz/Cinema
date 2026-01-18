package com.example.cinema.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey val id: Int,
    val page: Int,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val overview: String?,
    val title: String,
    val adult: Boolean,
    val isFavorite: Boolean
)
