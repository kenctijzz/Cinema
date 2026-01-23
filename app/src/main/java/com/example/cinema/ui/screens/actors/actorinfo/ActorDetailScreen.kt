package com.example.cinema.ui.screens.actors.actorinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.data.remote.ApiConstants
import com.example.cinema.ui.common.UiState
import com.example.cinema.ui.utils.UiError
import com.example.cinema.ui.utils.UiLoading

@Composable
fun ActorDetailScreen(
    actorDetailViewModel: ActorDetailViewModel = hiltViewModel()
) {
    val state = actorDetailViewModel.state.collectAsStateWithLifecycle()
    Box() {
        when (val uiState = state.value) {
            is UiState.Loading -> UiLoading()

            is UiState.Error -> UiError(actorDetailViewModel)

            is UiState.Success ->
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        alpha = 0.5f,
                        model = "${ApiConstants.ORIGINAL_IMAGE_BASE_URL}${uiState.data.image}",
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {

                        Text(
                            uiState.data.name,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    blurRadius = 8f
                                )
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "${
                                if (uiState.data.biography == "") {
                                    "This actor does not have biography "
                                } else {
                                    uiState.data.biography
                                }
                            }", textAlign = TextAlign.Justify,
                            fontSize = 32.sp,
                            modifier = Modifier.fillMaxSize(),
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    blurRadius = 8f
                                )
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "Birth: ${uiState.data.birthday}",
                            fontSize = 20.sp,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    blurRadius = 8f
                                )
                            )
                        )
                    }
                }
        }
    }
}