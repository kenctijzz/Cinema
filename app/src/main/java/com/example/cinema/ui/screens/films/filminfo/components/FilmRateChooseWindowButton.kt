package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FilmRateChooseWindowButton(snackbarHostState: SnackbarHostState) {
    var rateDialogVisible by remember { mutableStateOf(false) }
    if (rateDialogVisible) {
        FilmRateDialog(onDismiss = { rateDialogVisible = false }, snackbarHostState)
    }
    Column(modifier = Modifier.size(width = 40.dp, height = 60.dp)) {
        Box(
            modifier = Modifier
                .clickable(onClick = { rateDialogVisible = true }),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.StarBorder,
                contentDescription = "", Modifier.size(40.dp)
            )
        }
        Text(text = "Rate", textAlign = TextAlign.Justify)
    }
}