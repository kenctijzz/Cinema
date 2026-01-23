package com.example.cinema.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.cinema.ui.common.BaseViewModel
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun UiError(anyViewModel: BaseViewModel, errorText: String = "Ошибка при загрузке") {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(errorText)
        Button(onClick = { anyViewModel.load() }) {
            Text("Повторить")
        }
    }
}