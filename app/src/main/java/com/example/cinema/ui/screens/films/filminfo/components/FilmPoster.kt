package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun FilmPoster(
    filmPoster: String?,
    filmTitle: String?,
    filmReleaseDate: String?,
    filmRunTime: String?,
    filmRating: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Box {
            FilmPrimaryInfo(
                filmPoster = filmPoster,
                filmTitle = filmTitle,
                filmReleaseDate = filmReleaseDate,
                filmRunTime = filmRunTime
            )
            Box(
                modifier = Modifier
                    .size(height = 300.dp, width = 200.dp)
                    .alpha(0.95f)
                    .padding(2.dp),
                contentAlignment = Alignment.BottomStart,
            ) {
                FilmRatingCircle(filmRating = filmRating)
            }

        }


    }
}