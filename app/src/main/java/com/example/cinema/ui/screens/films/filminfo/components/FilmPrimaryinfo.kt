package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cinema.ui.utils.runTimeToString

@Composable
fun FilmPrimaryInfo(
    filmPoster: String,
    filmTitle: String,
    filmReleaseDate: String?,
    filmRunTime: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(height = 300.dp, width = 200.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(filmPoster)
                    .crossfade(true)
                    .build(),
                contentDescription = "Film Poster",
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            modifier = Modifier.width(200.dp),
            text = filmTitle,
            letterSpacing = (-0.5).sp,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "$filmReleaseDate ${filmRunTime.runTimeToString()}",
            textAlign = TextAlign.Center
        )
    }
}


