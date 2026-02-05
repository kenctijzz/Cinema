package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun BackgroundPoster(poster: String?) {
    Box(
        Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .blur(12.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(poster)
                .crossfade(true)
                .build(),
            contentDescription = "backgroundPoster",
            error = painterResource(id = com.example.cinema.R.drawable.ic_no_video),
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to Color.Black.copy(alpha = 0.2f),
                        0.5f to Color.Transparent,
                        1.0f to MaterialTheme.colorScheme.surfaceVariant
                    )
                )
        )
    }
}