package com.example.cinema.ui.screens.favoritefilms

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.ui.screens.filmlist.FilmInfo
import kotlinx.coroutines.flow.count

@Composable
fun FavoriteFilms(favoriteFilmsViewModel: FavoriteFilmsViewModel = hiltViewModel()) {
    val favoriteFilms by favoriteFilmsViewModel.favoriteFilms.collectAsStateWithLifecycle()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = favoriteFilms, key = {it.id}) { film ->
            FilmInfo(film = film)
        }
    }
}