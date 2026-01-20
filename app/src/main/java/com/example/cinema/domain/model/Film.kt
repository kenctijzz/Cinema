package com.example.cinema.domain.model

import androidx.room.PrimaryKey
import com.example.cinema.ui.common.VisualModels
import com.google.gson.annotations.SerializedName

data class Film(
    override val id: Int,
    val page: Int,
    val image: String?,
    val releaseDate: String?,
    val overview: String?,
    val title: String,
    val adult: Boolean,
    val isFavorite: Boolean
) : VisualModels