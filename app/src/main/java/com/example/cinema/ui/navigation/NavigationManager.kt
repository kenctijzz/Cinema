package com.example.cinema.ui.navigation

import com.example.cinema.core.navigation.UiNavigation
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


object NavigationManager {
    private val _events = MutableSharedFlow<UiNavigation<Any>>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val events: SharedFlow<UiNavigation<Any>> = _events

    suspend fun navigateTo(route: Any) {
        _events.emit(UiNavigation.NavigateTo(route))
    }

    suspend fun navigateBack() {
        _events.emit(UiNavigation.NavigateBack)
    }
}