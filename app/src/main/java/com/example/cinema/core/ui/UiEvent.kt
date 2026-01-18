package com.example.cinema.core.ui

sealed class UiEvent<T> {
    data class ShowSnackBar<T>(val message: String) : UiEvent<T>()
}