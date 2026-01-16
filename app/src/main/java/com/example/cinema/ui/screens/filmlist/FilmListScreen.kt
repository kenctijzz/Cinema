package com.example.cinema.ui.screens.filmlist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.core.ui.UiEvent

@Composable
fun FilmListScreen(
    filmViewModel: FilmViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    Log.d("PAGING_DEBUG", "FilmListScreen: Композиция началась")
    val pagedMovies = filmViewModel.filmsFlow.collectAsLazyPagingItems()

    LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
        if (pagedMovies.loadState.refresh is LoadState.Loading) {

            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        if (pagedMovies.loadState.refresh != LoadState.Loading) {
            items(
                count = pagedMovies.itemCount,
                key = { index ->
                    val film = pagedMovies[index]
                    "${film?.id ?: index}_$index"
                }) { index ->

                val film = pagedMovies[index]

                film?.let {
                    FilmInfo(film = film)
                }
            }
        }

        when (pagedMovies.loadState.append) {
            is LoadState.Loading -> {
                item { CircularProgressIndicator() }
            }

            is LoadState.Error -> {
                item {
                    Row() {
                        Text("Ошибка загрузки", color = MaterialTheme.colorScheme.error)
                        Button(onClick = { pagedMovies.retry() }) {
                            Text("Повторить")
                        }
                    }
                }

            }

            else -> {}
        }

    }
    if (pagedMovies.loadState.refresh is LoadState.Error) {
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
            Button(onClick = { pagedMovies.refresh() }) {
                Text("Повторить попытку")
            }
        }
    }

    LaunchedEffect(Unit) {
        filmViewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> snackBarHostState.showSnackbar(
                    event.message,
                    withDismissAction = true,
                )
            }
        }
    }
}