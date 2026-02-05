package com.example.cinema.ui.screens.films.filminfo.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cinema.data.remote.ApiConstants
import androidx.core.net.toUri

@Composable
fun FilmTrailerWatchButton(video: String?, title: String?, releaseYear: String) {
    val context = LocalContext.current

    val haveVideos = !video.isNullOrBlank()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (haveVideos) {
            Button(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        RoundedCornerShape(24.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.inverseSurface
                ),
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "${ApiConstants.YOUTUBE_URL}${video}".toUri()
                    )
                    context.startActivity(intent)

                }) {
                Text(
                    text = "Смотреть трейлер",
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 80.dp)
                .border
                    (
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    RoundedCornerShape(24.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.inverseSurface
            ),
            onClick = {
                Toast.makeText(context, "Ищем фильм в Вк Видео...", Toast.LENGTH_SHORT).show()
                val query = Uri.encode("$title $releaseYear")
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "${ApiConstants.VK_SEARCH}$query".toUri()
                )
                context.startActivity(intent)

            }) {
            Text(
                text = "Поиск в Вк Видео",
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
