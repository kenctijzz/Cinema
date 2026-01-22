package com.example.cinema.ui.screens.films.filminfo.components

import android.R.attr.fontWeight
import android.R.attr.onClick
import android.R.attr.text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.request.colorSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmRateDialog(onDismiss: () -> Unit) {
    val rateNumbers = remember {
        listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }
    var isSelectRate by remember { mutableStateOf(false) }
    var selectedRate by remember { mutableStateOf(0) }
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
            selectedRate = 0
        },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isSelectRate)
                LazyRow(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    items(items = rateNumbers) { number ->
                        key(number) {
                            Text(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable(onClick = {
                                        isSelectRate = true
                                        selectedRate = number
                                    }),
                                text = number.toString(),
                                color = when (number) {
                                    0 -> Color.Gray
                                    in 1..4 -> Color(0xFFE74C3C)
                                    in 5..6 -> Color.Gray
                                    in 7..10 -> Color(0xFF2ECC71)
                                    else -> {
                                        Color.Gray
                                    }
                                },
                                textAlign = TextAlign.Justify,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                            )
                        }
                    }
                }
            if (isSelectRate) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$selectedRate", fontWeight = FontWeight.ExtraBold,
                        fontSize = 64.sp,
                        color = when (selectedRate) {
                            0 -> Color.Gray
                            in 1..4 -> Color(0xFFE74C3C)
                            in 5..6 -> Color.Gray
                            in 7..10 -> Color(0xFF2ECC71)
                            else -> {
                                Color.Gray
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {},
                modifier = Modifier.width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isSelectRate) {
                        Color.Gray
                    } else {
                        when (selectedRate) {
                            0 -> Color.Gray
                            in 1..4 -> Color(0xFFE74C3C)
                            in 5..6 -> Color.Gray
                            in 7..10 -> Color(0xFF2ECC71)
                            else -> {
                                Color.Gray
                            }
                        }
                    },
                    contentColor = Color.White
                )
            ) {
                Text("Rate")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun testRateDialog() {
    FilmRateDialog(onDismiss = {})
}