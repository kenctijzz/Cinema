package com.example.cinema.core.ui

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}