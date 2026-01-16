package com.example.cinema.ui.screens.filmlist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
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
        when (pagedMovies.loadState.append) {
            is LoadState.Loading -> {
                item { CircularProgressIndicator() }
            }

            is LoadState.Error -> {
                item { Text("Ошибка загрузки") }
            }

            else -> {}
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