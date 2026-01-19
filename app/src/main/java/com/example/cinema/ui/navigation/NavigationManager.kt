package com.example.cinema.ui.navigation

import android.R.attr.tag
import android.R.id.message
import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.rememberCoroutineScope
import coil3.util.Logger
import com.example.cinema.core.navigation.UiNavigation
import com.example.cinema.ui.navigation.NavigationManager.currentScreen
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow


object NavigationManager {

    private val _events = MutableSharedFlow<UiNavigation<Any>>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    var currentScreen: Any = Screen.FilmList

    val events: SharedFlow<UiNavigation<Any>> = _events
    suspend fun navigateTo(route: Any) {
        _events.emit(UiNavigation.NavigateTo(route))
    }

    suspend fun navigateBack() {
        _events.emit(UiNavigation.NavigateBack)
    }
}