package com.example.cinema.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rated_films")
data class RatedFilmsEntity(
    @PrimaryKey val id: Int,
    val userRating: Int
)