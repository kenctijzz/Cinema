package com.example.cinema.ui.screens.films.filminfo.components.detailbuttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel

@Composable
fun FilmLikeButton(
    filmDetailViewModel: FilmDetailViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val film = filmDetailViewModel.filmFlow?.collectAsStateWithLifecycle()

    Column(modifier = Modifier.size(width = 80.dp, height = 80.dp)) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            IconButton(
                onClick = { filmDetailViewModel.toggleFilmLike(film?.value) },
                content = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = if (film?.value?.isFavorite ?: false) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = ""
                    )
                }
            )


        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Любимые",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}