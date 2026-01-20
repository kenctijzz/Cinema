package com.example.cinema.ui.common

import com.example.cinema.domain.model.Film

sealed class UiState<out T>() {
    data object Loading : UiState<Nothing>()
    data class Success<T>( val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}