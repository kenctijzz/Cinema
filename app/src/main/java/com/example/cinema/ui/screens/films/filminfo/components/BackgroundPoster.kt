package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import coil3.size.Size

@Composable
fun BackgroundPoster(poster: String) {
    Box(Modifier
        .fillMaxSize()
        .height(800.dp)) {
        AsyncImage(
            modifier = Modifier.fillMaxSize().blur(radius = 8.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(poster)
                .crossfade(true)
                .build(),
            contentDescription = "backgroundPoster",
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop,
            alpha = 0.25f
        )
    }
}