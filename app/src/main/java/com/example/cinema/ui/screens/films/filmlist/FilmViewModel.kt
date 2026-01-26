package com.example.cinema.ui.screens.films.filmlist

import android.R.attr.text
import android.R.attr.type
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.data.repository.SortType
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.usecases.films.GetFilmsUseCase
import com.example.cinema.domain.usecases.films.ToggleFilmLikeUseCase
import com.example.cinema.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FilmViewModel @Inject constructor(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val toggleFilmLikeUseCase: ToggleFilmLikeUseCase
) : BaseViewModel() {
    private val _snackBarEvent = MutableSharedFlow<UiEvent.ShowSnackBar>(
        replay = 0,
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val snackBarEvent: MutableSharedFlow<UiEvent.ShowSnackBar> = _snackBarEvent
    val searchText = MutableStateFlow("")

    suspend fun showSnackBar(message: String) {
        _snackBarEvent.emit(UiEvent.ShowSnackBar(message))
    }

    val filmsSortType = MutableStateFlow(SortType.POPULARITY)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filmsFlow = combine(
        filmsSortType,
        searchText.debounce(200L)
    ) { type, text ->
        type to text
    }.flatMapLatest { (type, text) ->
        getFilmsUseCase(type, text)
    }.cachedIn(viewModelScope)

    fun changeFilmsSortType(sortType: SortType) {
        filmsSortType.value = sortType
        Log.d("DEBUG", "${filmsSortType.value}")
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

    init {
        Log.d("DEBUG", "VM Hash: ${this.hashCode()}")
    }

    override fun load() {
    }

    fun searchTextChange(search: String) {
        searchText.value = search
    }
}