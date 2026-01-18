package com.example.cinema.ui.screens.filminfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.cinema.ui.navigation.Screen
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.domain.repository.FilmRepository
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
    private val repository: FilmRepository
) : ViewModel() {
    private val characterId: Int = savedStateHandle.toRoute<Screen.FilmDetail>().id
    private val _state = MutableStateFlow<UiState<FilmEntity>>(UiState.Loading)
    val state: StateFlow<UiState<FilmEntity>> = _state

    init {
        loadFilm()
    }

    fun loadFilm() {
        viewModelScope.launch {
            _state.update {
                UiState.Loading
            }
            repository.getFilmById(characterId).onSuccess { data ->
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
}