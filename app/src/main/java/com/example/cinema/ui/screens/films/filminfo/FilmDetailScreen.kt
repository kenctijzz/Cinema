package com.example.cinema.ui.screens.films.filminfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.data.remote.ApiConstants
import com.example.cinema.ui.common.UiState
import com.example.cinema.ui.screens.films.filminfo.components.FilmPoster

@Composable
fun FilmDetailScreen(
    filmDetailViewModel: FilmDetailViewModel = hiltViewModel(),
) {
    val state = filmDetailViewModel.state.collectAsStateWithLifecycle()
    Box() {
        when (val uiState = state.value) {
            is UiState.Loading ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }

            is UiState.Error ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Ошибка при загрузке")
                    Button(onClick = { filmDetailViewModel.loadFilm() }) {
                        Text("Повторить")
                    }
                }

            is UiState.Success ->
                Box(modifier = Modifier.fillMaxSize()) {
                    FilmPoster(
                        "${ApiConstants.ORIGINAL_IMAGE_BASE_URL}${uiState.data.image}                                                                   ",
                        filmTitle = uiState.data.title,
                        filmReleaseDate = uiState.data.releaseDate,
                        filmRunTime = uiState.data.runtime,
                        filmRating = uiState.data.rating
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                    }
                }
        }
    }

}