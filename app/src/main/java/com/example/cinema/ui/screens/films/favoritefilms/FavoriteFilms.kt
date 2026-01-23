package com.example.cinema.ui.screens.films.favoritefilms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.ui.common.UiState
import com.example.cinema.ui.navigation.NavigationManager
import com.example.cinema.ui.navigation.Screen
import com.example.cinema.ui.screens.films.filmlist.FilmInfo
import com.example.cinema.ui.utils.UiError
import com.example.cinema.ui.utils.UiLoading
import kotlinx.coroutines.launch

@Composable
fun FavoriteFilms(
    favoriteFilmsViewModel: FavoriteFilmsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()

    val state by favoriteFilmsViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        favoriteFilmsViewModel.snackBarEvent.collect { event ->
            if (event is UiEvent.ShowSnackBar) {
                snackbarHostState.showSnackbar(message = event.message)
            }
        }
    }
    when (val currentState = state) {
        is UiState.Loading -> UiLoading()

        is UiState.Error -> UiError(favoriteFilmsViewModel)

        is UiState.Success -> {
            if (currentState.data.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Ваш список любимых фильмов пуст",
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 32.sp, fontFamily = FontFamily.Monospace
                    )
                    Button(onClick = {
                        scope.launch {
                            NavigationManager.navigateTo(Screen.FilmList)
                        }
                    }) {
                        Text("Вернуться на главную")
                    }
                }
            } else {
                LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
                    items(items = currentState.data, key = { it.id }) { film ->
                        FilmInfo(
                            film = film,
                            onLikeClick = { favoriteFilmsViewModel.toggleFilmLike(film) })
                    }
                }
            }
        }
    }
}