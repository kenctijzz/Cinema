package com.example.cinema.domain.model

import com.example.cinema.ui.common.VisualModels

data class Actor (
    val type: String = "actor",
    override val id: Int,
    val page: Int,
    val gender: Int,
    val popularity: Double,
    val birthday: String?,
    val deathday: String?,
    val biography: String?,
    val name: String,
    val image: String?,
    val isFavorite: Boolean
): VisualModels