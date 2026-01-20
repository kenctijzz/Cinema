package com.example.cinema.data.remote.actors.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ActorModel(
    val id: Int,
    val gender: Int,
    val name: String,
    @SerializedName("profile_path")
    val image: String,
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val popularity: Double?

)
data class ActorImageModel(
    @SerializedName("file_path")
    val actorImage: String?
)