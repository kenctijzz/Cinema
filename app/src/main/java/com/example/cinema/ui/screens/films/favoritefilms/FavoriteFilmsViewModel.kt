package com.example.cinema.ui.screens.films.favoritefilms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.usecases.films.GetFavoriteFilmsUseCase
import com.example.cinema.domain.usecases.films.ToggleFilmLikeUseCase
import com.example.cinema.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFilmsViewModel @Inject constructor(
    private val toggleFilmLikeUseCase: ToggleFilmLikeUseCase,
    private val getFavoriteFilmsUseCase: GetFavoriteFilmsUseCase,
) : ViewModel() {
    private val _snackBarEvent = MutableSharedFlow<UiEvent>(
        replay = 0,
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackBarEvent: SharedFlow<UiEvent> = _snackBarEvent
    val state: StateFlow<UiState<List<Film>>> = getFavoriteFilmsUseCase().map { films ->
        UiState.Success(films)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    suspend fun showSnackBar(message: String) {
        _snackBarEvent.emit(UiEvent.ShowSnackBar(message))
    }

    fun toggleFilmLike(film: Film) {
        viewModelScope.launch {
            val favoriteStatus = toggleFilmLikeUseCase(film)
            if (favoriteStatus) {
                showSnackBar("${film.title} добавлен в избранное")
            } else {
                showSnackBar("${film.title} удален из избранного")
            }
        }
    }
}
