package com.example.cinema.ui.screens.films.filmlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.usecases.films.GetPopularFilmsUseCase
import com.example.cinema.domain.usecases.films.ToggleFilmLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FilmViewModel @Inject constructor(
    private val getPopularFilmsUseCase: GetPopularFilmsUseCase,
    private val toggleFilmLikeUseCase: ToggleFilmLikeUseCase,
) : ViewModel() {
    private val _snackBarEvent = MutableSharedFlow<UiEvent.ShowSnackBar>(
        replay = 0,
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackBarEvent: MutableSharedFlow<UiEvent.ShowSnackBar> = _snackBarEvent

    suspend fun showSnackBar(message: String) {
        _snackBarEvent.emit(UiEvent.ShowSnackBar(message))
    }

    val filmsFlow: Flow<PagingData<Film>> =
        getPopularFilmsUseCase().cachedIn(viewModelScope)

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