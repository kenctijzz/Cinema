package com.example.cinema.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "actors")
data class ActorEntity (
    @PrimaryKey val id: Int,
    val page: Int,
    val gender: Int,
    val popularity: Double?,
    val birthday: String?,
    val deathday: String?,
    val biography: String?,
    val name: String,
    @SerializedName("profile_path")
    val image: String?,
    val isFavorite: Boolean
)