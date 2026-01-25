package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinema.domain.model.Film

@Composable
fun FilmDetailButtons(snackbarHostState: SnackbarHostState, filmId: Int, film: Film) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            FilmRateChooseWindowButton(
                film = film,
                snackbarHostState = snackbarHostState,
                filmId = filmId
            )
            Spacer(modifier = Modifier.width(2.dp))
            FilmUserRating()
            FilmLikeButton(snackbarHostState = snackbarHostState)

        }
    }
}