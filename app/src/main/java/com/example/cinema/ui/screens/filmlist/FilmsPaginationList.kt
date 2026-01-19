package com.example.cinema.ui.screens.filmlist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.cinema.data.local.entities.FilmEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/*
@Composable
fun FilmsPaginationList(pagedMovies: LazyPagingItems<FilmEntity>,
                        filmViewModel: FilmViewModel = hiltViewModel()
) {
    when (pagedMovies.loadState.refresh) {
        is LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
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

        is LoadState.NotLoading -> {
            LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
                items(
                    count = pagedMovies.itemCount,
                    key = pagedMovies.itemKey { it.id })
                { index ->

                    val film = pagedMovies[index]

                    film?.let {
                        FilmInfo(film = film, onLikeClick = { filmViewModel.toggleFilmLike(film) })
                    }
                }
                when (pagedMovies.loadState.append) {
                    is LoadState.Loading -> {
                        item(span = { GridItemSpan(2) }) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is LoadState.Error -> {
                        item(span = { GridItemSpan(2) }) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
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

        }
    }
*/
/*    LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
        items(
            count = pagedMovies.itemCount,
            key = pagedMovies.itemKey { it.id })
        { index ->

            val film = pagedMovies[index]

            film?.let {
                FilmInfo(film = film, onLikeClick = { filmViewModel.toggleFilmLike(film) })
            }
        }
    }*//*

}*/
