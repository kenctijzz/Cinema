package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinema.ui.screens.films.filminfo.components.detailbuttons.FilmRateButton
import com.example.cinema.ui.theme.toRatingColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmRateDialog(
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState,
    filmId: Int
) {

    val rateNumbers = remember {
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }
    var isSelectRate by remember { mutableStateOf(false) }
    var showValidText by remember { mutableStateOf(false) }
    var selectedRate by remember { mutableStateOf(-1) }
    BasicAlertDialog(
        onDismissRequest = {
            onDismiss()
            isSelectRate = false
            selectedRate = -1
        },
    ) {
        Card(
            modifier = Modifier
                .alpha(0.96f)
                .size(height = if (!isSelectRate) 390.dp else 200.dp, width = 120.dp)
                .clip(RoundedCornerShape(32.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (!isSelectRate)
                    RatingSelectColumn(
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
                    onDismiss = onDismiss,
                    filmId = filmId,
                    selectedRate = selectedRate,
                    showValidText = { newValue -> showValidText = newValue },
                    isSelectRate = isSelectRate
                )
            }
        }
    }
}

