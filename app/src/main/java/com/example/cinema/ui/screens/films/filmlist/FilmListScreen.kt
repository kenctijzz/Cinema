package com.example.cinema.ui.screens.films.filmlist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.ui.components.PagingDataVerticalGrid
import com.example.cinema.ui.navigation.TopAppBarNav
import com.example.cinema.ui.utils.UiError
import com.example.cinema.ui.utils.UiLoading
import kotlinx.coroutines.delay

@Composable
fun FilmListScreen(
    filmViewModel: FilmViewModel,
    snackbarHostState: SnackbarHostState,
) {


    val pagedMovies = filmViewModel.filmsFlow.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()
    val currentSort by filmViewModel.filmsSortType.collectAsState()
    var isFirstComposition by remember { mutableStateOf(true) }
    LaunchedEffect(currentSort) {
        if (isFirstComposition) {
            isFirstComposition = false
        } else {
            gridState.animateScrollToItem(0)
            delay(50)
            gridState.animateScrollToItem(0)
        }
    }
    val isRefreshing =
        pagedMovies.loadState.refresh is LoadState.Loading && pagedMovies.itemCount > 0
    LaunchedEffect(Unit) {
        filmViewModel.snackBarEvent.collect { event ->
            if (event is UiEvent.ShowSnackBar) {
                snackbarHostState.showSnackbar(message = event.message)
            }
        }
    }

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = {
            pagedMovies.refresh()
        }
    ) {

        when (pagedMovies.loadState.refresh) {

            is LoadState.Loading -> {
                if (pagedMovies.itemCount == 0) {
                    UiLoading()
                }
            }

            is LoadState.Error -> {
                UiError(
                    anyViewModel = filmViewModel,
                    errorText = "Проблемы с доступом.Проверьте подключение к VPN"
                )
            }

            else -> {
                PagingDataVerticalGrid(anyPagingData = pagedMovies, state = gridState) { film ->
                    FilmInfo(
                        film = film,
                        onLikeClick = { filmViewModel.toggleFilmLike(film) }
                    )
                }
            }
        }
    }
}