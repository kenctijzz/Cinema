package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel
import com.example.cinema.ui.theme.toRatingColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmRateDialog(
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState,
    filmDetailViewModel: FilmDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        filmDetailViewModel.snackBarEvent.collect { event ->
            if (event is UiEvent.ShowSnackBar) {
                snackbarHostState.showSnackbar(message = event.message)
            }
        }
    }
    val rateNumbers = remember {
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }
    var isSelectRate by remember { mutableStateOf(false) }
    var showValidText by remember { mutableStateOf(false) }
    var selectedRate by remember { mutableStateOf(-1) }
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
            selectedRate = -1
        },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isSelectRate)
                RatingSelectRow(
                    rateNumbers = rateNumbers,
                    showValidText = { showValidText = false },
                    selectedRate = { newRate -> selectedRate = newRate },
                    isSelectRate = { isSelectRate = true })
            if (isSelectRate) {
                Text(
                    text = "$selectedRate",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 64.sp,
                    color = selectedRate.toDouble().toRatingColor()
                )
            }
            if (showValidText) {
                Text("Choose rate", color = MaterialTheme.colorScheme.error)
            }
            FilmRateButton(
                selectedRate = selectedRate,
                showValidText = { newValue -> showValidText = newValue },
                isSelectRate = isSelectRate
            )
        }
    }
}

