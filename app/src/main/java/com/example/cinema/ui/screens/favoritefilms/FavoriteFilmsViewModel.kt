package com.example.cinema.ui.screens.favoritefilms

import androidx.compose.ui.input.key.Key.Companion.U
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.repository.FilmRepositoryImpl
import com.example.cinema.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFilmsViewModel @Inject constructor(
    private val repository: FilmRepositoryImpl
) : ViewModel() {
    val favoriteFilms: StateFlow<List<FilmEntity>> = repository.getFavoriteFilms()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
