package com.example.cinema.ui.screens.films.filminfo.components

import android.R.attr.text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
import com.example.cinema.ui.utils.saveCoilImageToGallery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FilmPhotoDropDownMenu(
    photo: String, onDismissRequest: () -> Unit,
    showSnackBar: () -> Unit,
    scope: CoroutineScope,
) {
    val context = LocalContext.current
    DropdownMenu(
        modifier = Modifier.alpha(0.96f),
        onDismissRequest = { onDismissRequest() },
        expanded = true
    ) {
        DropdownMenuItem(
            text = { Text("Скачать") },
            onClick = {
                onDismissRequest()
                scope.launch {
                    saveCoilImageToGallery(context, photo, showSnackBar)
                }
            }
        )
    }
}
