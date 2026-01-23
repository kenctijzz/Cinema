package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinema.ui.theme.toRatingColor

@Composable
fun RatingSelectRow(rateNumbers: List<Int>, showValidText: () -> Unit,
                    isSelectRate: () -> Unit,
                    selectedRate: (Int) -> Unit){
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(50.dp)
    )
    {
        items(items = rateNumbers) { number ->
            key(number) {
                Text(
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable(onClick = {
                            showValidText()
                            isSelectRate()
                            selectedRate(number)
                        }),
                    text = number.toString(),
                    color = number.toDouble().toRatingColor(),
                    textAlign = TextAlign.Justify,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}