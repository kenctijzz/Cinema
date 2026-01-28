package com.example.cinema.ui.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.paging.compose.LazyPagingItems
import com.example.cinema.ui.common.BaseViewModel
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun UiError(anyViewModel: BaseViewModel, errorText: String = "Не удалось загрузить данные. Проверьте соединение и попробуйте снова.") {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(errorText, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp)
            Button(
                modifier = Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    RoundedCornerShape(24.dp)
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.inverseSurface
                ), onClick = { anyViewModel.load() }) {
                Text("Повторить")
            }
    }
}