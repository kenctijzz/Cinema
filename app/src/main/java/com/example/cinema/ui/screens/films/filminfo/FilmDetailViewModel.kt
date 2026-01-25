package com.example.cinema.ui.screens.films.filminfo

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.ui.navigation.Screen
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.usecases.films.GetFilmDetailsUseCase
import com.example.cinema.domain.usecases.films.GetFilmFlowUseCase
import com.example.cinema.domain.usecases.films.ToggleFilmLikeUseCase
import com.example.cinema.ui.common.BaseViewModel
import com.example.cinema.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val toggleFilmLikeUseCase: ToggleFilmLikeUseCase,
    private val getFilmFlowUseCase: GetFilmFlowUseCase
) : BaseViewModel() {
    private val _snackBarEvent = MutableSharedFlow<UiEvent.ShowSnackBar>(
        replay = 0,
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackBarEvent: MutableSharedFlow<UiEvent.ShowSnackBar> = _snackBarEvent

    suspend fun showSnackBar(message: String) {
        _snackBarEvent.emit(UiEvent.ShowSnackBar(message))
    }

    private val filmId: Int = savedStateHandle.toRoute<Screen.FilmDetail>().id
    private val _state = MutableStateFlow<UiState<Film>>(UiState.Loading)

    val filmFlow: StateFlow<Film> = getFilmFlowUseCase(filmId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Film(
            0, 0, null, null, null, "",
            adult = false, isFavorite = false, 0.0, 0.0, "",
            0, video = null, photos = emptyList()
        )

    )
    val state: StateFlow<UiState<Film>> = _state
    override fun load() {
        viewModelScope.launch {
            _state.update {
                UiState.Loading
            }

            getFilmDetailsUseCase(filmId).onSuccess { data ->
                _state.update {
                    UiState.Success(data)
                }
            }.onFailure { error ->
                _state.update {
                    Log.e("ERROR OF LOADING FILM INFO","$error")
                    UiState.Error("$error")
                }
            }
        }
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
    fun successImageSave() {
        viewModelScope.launch {
            showSnackBar("Saved in gallery")
        }
    }
    init {
        load()
    }
}