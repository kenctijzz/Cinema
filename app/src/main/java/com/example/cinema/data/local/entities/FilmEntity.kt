package com.example.cinema.data.local.entities

import android.util.Log.v
import androidx.room.Entity
import androidx.room.Ignore
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
    val isFavorite: Boolean,
    @SerializedName("vote_average")
    val rating: Double,
    val popularity: Double,
    @SerializedName("original_language")
    val language: String,
    @SerializedName("runtime")
    val runtime: Int,
    val video: String?
)