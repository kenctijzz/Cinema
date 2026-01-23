package com.example.cinema.ui.screens.films.filminfo.components

import android.R.attr.text
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.data.remote.ApiConstants
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel

@Composable
fun FilmTrailerWatchButton(filmDetailViewModel: FilmDetailViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val film by filmDetailViewModel.filmFlow.collectAsStateWithLifecycle()
    val haveVideos = !film.video.isNullOrBlank()
    if (haveVideos) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(modifier = Modifier.alpha(0.8f),
                onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("${ApiConstants.YOUTUBE_URL}${film.video}")
                )
                context.startActivity(intent)

            }) {
                Text("Watch About Film")
            }
        }
    }
}
