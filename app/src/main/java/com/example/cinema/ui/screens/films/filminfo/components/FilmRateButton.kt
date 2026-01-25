package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel
import com.example.cinema.ui.theme.toRatingColor
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun FilmRateButton(
    selectedRate: Int, showValidText: (Boolean) -> Unit, isSelectRate: Boolean,
    filmId: Int,
    onDismiss: () -> Unit,
    filmDetailViewModel: FilmDetailViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    Button(
        onClick = {
            scope.launch {
                filmDetailViewModel.updateFilmRating(newRating = selectedRate, id = filmId)
            }
            onDismiss()
        },
        modifier = Modifier.width(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (!isSelectRate) {
                Color.Gray
            } else {
                selectedRate.toDouble().toRatingColor()
            },
            contentColor = Color.White
        )
    ) {
        Text("Rate")
    }
}