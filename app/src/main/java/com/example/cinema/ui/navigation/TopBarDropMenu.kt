package com.example.cinema.ui.navigation

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TopBarDropMenu(
    sortByPopularityClick: () -> Unit,
    sortByUserRatingClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp), horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .padding(vertical = 16.dp)
        ) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { onDismiss() }
            )
            {
                DropdownMenuItem(
                    text = { Text("Sort by popularity") },
                    onClick = {
                        sortByPopularityClick()
                        onDismiss()
                    })

                DropdownMenuItem(
                    text = { Text("Sort by my rating") },
                    onClick = {
                        sortByUserRatingClick()
                        onDismiss()
                    })
            }
        }
    }
}