package com.example.cinema.domain.model

import com.example.cinema.ui.common.VisualModels

data class Film(
    override val id: Int,
    val page: Int,
    val image: String?,
    val releaseDate: String?,
    val overview: String?,
    val title: String?,
    val adult: Boolean,
    val isFavorite: Boolean,
    val rating: Double,
    val popularity: Double,
    val language: String?,
    val runtime: String?,
    val video: String?,
    val photos: List<String>,
    val userRating: Int?
) : VisualModels