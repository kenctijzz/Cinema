package com.example.cinema.core.navigation

sealed interface UiNavigation<out T> {
    data class NavigateTo<T>(val route: T) : UiNavigation<T>
    data object NavigateBack : UiNavigation<Nothing>
}
