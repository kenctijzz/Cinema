package com.example.cinema.ui.screens.films.filminfo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.data.remote.ApiConstants
import com.example.cinema.ui.common.UiState
import com.example.cinema.ui.screens.films.filminfo.components.BackgroundPoster
import com.example.cinema.ui.screens.films.filminfo.components.detailbuttons.FilmDetailButtons
import com.example.cinema.ui.screens.films.filminfo.components.FilmOverview
import com.example.cinema.ui.screens.films.filminfo.components.FilmPhotos
import com.example.cinema.ui.screens.films.filminfo.components.FilmPoster
import com.example.cinema.ui.screens.films.filminfo.components.FilmTrailerWatchButton
import com.example.cinema.ui.utils.UiError
import com.example.cinema.ui.utils.UiLoading
import kotlinx.coroutines.launch

@Composable
fun FilmDetailScreen(
    filmDetailViewModel: FilmDetailViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val backgroundAlpha = (1f - (scrollState.value / 1000f)).coerceIn(0f, 1f)
    val state = filmDetailViewModel.state.collectAsStateWithLifecycle()
    Box() {
        LaunchedEffect(Unit) {
            filmDetailViewModel.snackBarEvent.collect { event ->
                if (event is UiEvent.ShowSnackBar) {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
        when (val uiState = state.value) {
            is UiState.Loading ->
                UiLoading()

            is UiState.Error ->
                UiError(filmDetailViewModel)

            is UiState.Success -> {
                var animateSuccess by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    animateSuccess = true
                }
                AnimatedVisibility(
                    visible = animateSuccess,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500))
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.graphicsLayer { alpha = backgroundAlpha }) {
                            BackgroundPoster("${ApiConstants.ORIGINAL_IMAGE_BASE_URL}${uiState.data.image}")
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Spacer(modifier = Modifier.height(32.dp))
                            FilmPoster(
                                "${ApiConstants.ORIGINAL_IMAGE_BASE_URL}${uiState.data.image}",
                                filmTitle = uiState.data.title,
                                filmReleaseDate = uiState.data.releaseDate?.replace('-', '.') ?: "Дата неизвестна",
                                filmRunTime = uiState.data.runtime,
                                filmRating = uiState.data.rating
                            )
                            FilmTrailerWatchButton(uiState.data.video)
                            FilmDetailButtons(
                                film = uiState.data,
                                snackbarHostState = snackbarHostState,
                                filmId = uiState.data.id
                            )
                            FilmOverview(uiState.data.overview ?: "Film has no overview")
                            FilmPhotos(
                                uiState.data.photos,
                                showSnackBar = { scope.launch { filmDetailViewModel.successImageSave() } })
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }
    }
}