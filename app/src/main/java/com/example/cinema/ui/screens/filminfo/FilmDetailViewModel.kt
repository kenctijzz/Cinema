package com.example.cinema.ui.screens.filminfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cinema.ui.navigation.Screen
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import com.example.cinema.domain.usecase.GetFilmDetailsUseCase
import com.example.cinema.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val characterId: Int = savedStateHandle.toRoute<Screen.FilmDetail>().id
    private val _state = MutableStateFlow<UiState<Film>>(UiState.Loading)
    val state: StateFlow<UiState<Film>> = _state

    fun loadFilm() {
        viewModelScope.launch {
            _state.update {
                UiState.Loading
            }
            getFilmDetailsUseCase(characterId).onSuccess { data ->
                _state.update {
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