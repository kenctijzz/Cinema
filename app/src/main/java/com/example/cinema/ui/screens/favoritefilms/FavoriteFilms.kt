package com.example.cinema.ui.screens.favoritefilms

import android.R.id.message
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.ui.navigation.NavigationManager
import com.example.cinema.ui.navigation.Screen
import com.example.cinema.ui.screens.filmlist.FilmInfo
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

@Composable
fun FavoriteFilms(
    favoriteFilmsViewModel: FavoriteFilmsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        favoriteFilmsViewModel.snackBarEvent.collect { event ->
            if (event is UiEvent.ShowSnackBar) {
                snackbarHostState.showSnackbar(message = event.message)
            }
        }
    }

    val favoriteFilms by favoriteFilmsViewModel.favoriteFilms.collectAsStateWithLifecycle()

    if (favoriteFilms.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Ваш список любимых фильмов пуст",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.error,
                fontFamily = FontFamily.Monospace
            )
            Button(onClick = {
                scope.launch {
                    NavigationManager.navigateTo(Screen.FilmList)
                }
            }
            ) {
                Text("Вернуться на главную")
            }
        }
    } else {
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
            items(items = favoriteFilms, key = { it.id }) { film ->
                FilmInfo(
                    film = film,
                    onLikeClick = { favoriteFilmsViewModel.toggleFilmLike(film) })
            }
        }
    }
}