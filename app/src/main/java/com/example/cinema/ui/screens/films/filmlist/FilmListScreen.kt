package com.example.cinema.ui.screens.films.filmlist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.ui.components.PagingDataVerticalGrid

@Composable
fun FilmListScreen(
    filmViewModel: FilmViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val pagedMovies = filmViewModel.filmsFlow.collectAsLazyPagingItems()
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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is LoadState.Error -> {
                Log.e(
                    "PagingError",
                    "Причина: ",
                    (pagedMovies.loadState.refresh as LoadState.Error).error
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Проблемы с доступом. Проверьте подключение к VPN",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = { pagedMovies.retry() }) {
                        Text("Повторить попытку")
                    }
                }
            }

            else -> PagingDataVerticalGrid(anyPagingData = filmViewModel.filmsFlow) {film ->
                FilmInfo(
                    film = film,
                    onLikeClick = {filmViewModel.toggleFilmLike(film)}
                )
            }
        }
    }
}