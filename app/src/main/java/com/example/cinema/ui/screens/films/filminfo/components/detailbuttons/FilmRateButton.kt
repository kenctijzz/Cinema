package com.example.cinema.ui.screens.films.filminfo.components.detailbuttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel
import com.example.cinema.ui.theme.toRatingColor
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
                    if(selectedRate < 100) {
                        println("$selectedRate")
                        filmDetailViewModel.updateFilmRating(newRating = selectedRate, id = filmId)
                    }
                    else{
                        println("$selectedRate")
                        filmDetailViewModel.deleteFilmUserRating(id = filmId)
                    }

                }
            onDismiss()
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (!isSelectRate || selectedRate == 100) {
                Color.Gray
            } else {
                selectedRate.toDouble().toRatingColor()
            },
            contentColor = Color.White
        )
    ) {
        Text(if(selectedRate != 100)"Оценить" else "Удалить оценку")
    }
}