package com.example.cinema.ui.screens.films.filminfo.components

import android.R.attr.contentDescription
import android.R.attr.text
import android.util.Log.i
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Start
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FilmRateButton() {
    var rateDialogVisible by remember { mutableStateOf(false) }
    if (rateDialogVisible) {
        FilmRateDialog(onDismiss = { rateDialogVisible = false })
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Text(text = "Rate")
    }
}