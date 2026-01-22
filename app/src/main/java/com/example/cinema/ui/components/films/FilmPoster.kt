package com.example.cinema.ui.components.films

import android.R.attr.contentDescription
import android.R.attr.fontWeight
import android.R.attr.text
import android.util.Log.i
import android.util.Log.w
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cinema.R
import com.example.cinema.ui.utils.runTimeToString

@Composable
fun FilmPoster(
    filmPoster: String,
    filmTitle: String,
    filmReleaseDate: String?,
    filmRunTime: Int,
    filmRating: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Box(
            modifier = Modifier,
        )
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
            Box(modifier = Modifier) {
                Box(
                    modifier = Modifier
                        .background(color = Color.Black.copy(alpha = 0.5f))
                        .size(height = 25.dp, width = 30.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 2.dp),
                    text = "$filmRating",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        filmRating >= 8.50 -> Color(0xFF2ECC71)
                        filmRating >= 6.50 -> Color(0xFFF1C40F)
                        filmRating >= 5 -> Color(0xFFE67E22)
                        else -> Color(0xFFE74C3C)
                    }
                )
            }

        }
        Text(
            text = filmTitle,
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