package com.example.cinema.ui.screens.filmlist

import android.R.id.message
import android.util.Log
import androidx.compose.foundation.rememberPlatformOverscrollFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.dto.FilmModel
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import com.example.cinema.domain.usecase.GetPopularFilmsUseCase
import com.example.cinema.domain.usecase.ToggleFilmLikeUseCase
import com.example.cinema.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FilmViewModel @Inject constructor(
    private val getPopularFilmsUseCase: GetPopularFilmsUseCase,
    private val toggleFilmLikeUseCase: ToggleFilmLikeUseCase,
) : ViewModel() {
    private val _snackBarEvent = MutableSharedFlow<UiEvent<Any>>(
        replay = 0,
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackBarEvent: MutableSharedFlow<UiEvent<Any>> = _snackBarEvent

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