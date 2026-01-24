package com.example.cinema.ui.screens.films.filminfo.components

import android.R.attr.height
import android.R.attr.onClick
import android.graphics.ColorMatrix
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.domain.model.Film
import com.example.cinema.ui.common.UiState
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel

@Composable
fun FilmLikeButton(
    filmDetailViewModel: FilmDetailViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val film by filmDetailViewModel.filmFlow.collectAsStateWithLifecycle()
    
    Column(modifier = Modifier.size(width = 40.dp, height = 60.dp)) {
        Box(modifier = Modifier.clickable(onClick = { filmDetailViewModel.toggleFilmLike(film) })) {
            Icon(
                imageVector =
                    if (film.isFavorite) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    }, contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Like",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}