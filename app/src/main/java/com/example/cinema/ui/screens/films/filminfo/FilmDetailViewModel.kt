package com.example.cinema.ui.screens.films.filminfo

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cinema.ui.navigation.Screen
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.usecases.films.GetFilmDetailsUseCase
import com.example.cinema.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase
) : ViewModel() {
    private val filmId: Int = savedStateHandle.toRoute<Screen.FilmDetail>().id
    private val _state = MutableStateFlow<UiState<Film>>(UiState.Loading)
    val state: StateFlow<UiState<Film>> = _state

    fun loadFilm() {
        viewModelScope.launch {
            _state.update {
                UiState.Loading
            }

            getFilmDetailsUseCase(filmId).onSuccess { data ->
                _state.update {
                    Log.e("runtime info in viewModel", "${data.runtime}")
                    UiState.Success(data)
                }
            }.onFailure { error ->
                _state.update {
                    UiState.Error("$error")
                }
            }
        }

    }

    init {
        loadFilm()
    }
}