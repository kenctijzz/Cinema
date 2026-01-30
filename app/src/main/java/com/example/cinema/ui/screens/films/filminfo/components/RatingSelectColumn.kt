package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinema.ui.theme.toRatingColor

@Composable
fun RatingSelectColumn(
    rateNumbers: List<Int>, showValidText: () -> Unit,
    isSelectRate: () -> Unit,
    selectedRate: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    )
    {
        item() {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.surfaceVariant)
                        .size(60.dp)
                        .clickable(onClick = {
                            showValidText()
                            isSelectRate()
                            selectedRate(100)
                        })
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    text = "-",
                    color = 0.toDouble().toRatingColor(),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
        items(items = rateNumbers) { number ->
            key(number) {
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.surfaceVariant)
                            .size(60.dp)
                            .clickable(onClick = {
                                showValidText()
                                isSelectRate()
                                selectedRate(number)
                            })
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        text = number.toString(),
                        color = number.toDouble().toRatingColor(),
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }

}
}