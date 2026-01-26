package com.example.cinema.ui.screens.films.filminfo.components.detailbuttons

import android.R.attr.contentDescription
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cinema.domain.model.Film
import com.example.cinema.ui.screens.films.filminfo.components.FilmRateDialog

@Composable
fun FilmRateChooseWindowButton(
    snackbarHostState: SnackbarHostState,
    filmId: Int,
    film: Film
) {
    var rateDialogVisible by remember { mutableStateOf(false) }
    if (rateDialogVisible) {
        FilmRateDialog(
            onDismiss = { rateDialogVisible = false },
            snackbarHostState,
            filmId = filmId
        )
    }
    Column(modifier = Modifier.size(width = 80.dp, height = 80.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { rateDialogVisible = true },
                content = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = ""
                    )
                }
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Оценить",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}