package com.example.cinema.ui.screens.favoritefilms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.repository.FilmRepositoryImpl
import com.example.cinema.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFilmsViewModel @Inject constructor(
    private val repository: FilmRepositoryImpl
) : ViewModel() {
    private val _snackBarEvent = MutableSharedFlow<UiEvent<Any>>(
        replay = 0,
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackBarEvent: MutableSharedFlow<UiEvent<Any>> = _snackBarEvent
    val state: StateFlow<UiState<List<FilmEntity>>> = repository.getFavoriteFilms()
        .map { films -> UiState.Success(films) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    suspend fun showSnackBar(message: String) {
        _snackBarEvent.emit(UiEvent.ShowSnackBar(message))
    }

    fun toggleFilmLike(film: FilmEntity) {
        viewModelScope.launch {
            repository.toggleFilmLike(film.id)
            val message = if (!film.isFavorite) {
                "${film.title} добавлен в избранное"
            } else {
                "${film.title} удален из избранного"
            }
            showSnackBar(message)
        }

    }
}
