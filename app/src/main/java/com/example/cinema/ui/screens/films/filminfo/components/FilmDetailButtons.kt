package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilmDetailButtons(snackbarHostState: SnackbarHostState) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            FilmRateChooseWindowButton(snackbarHostState = snackbarHostState)
            FilmLikeButton(snackbarHostState = snackbarHostState)
        }
    }
}