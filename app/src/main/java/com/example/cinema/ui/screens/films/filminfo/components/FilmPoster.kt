package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun FilmPoster(
    filmPoster: String,
    filmTitle: String,
    filmReleaseDate: String?,
    filmRunTime: Int,
    filmRating: Double
) {
    var animate by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animate = true
    }
    AnimatedVisibility(
        visible = animate,
        enter = fadeIn(animationSpec = tween(durationMillis = 500))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
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
}