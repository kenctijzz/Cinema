package com.example.cinema.ui.screens.films.filminfo.components

import android.R.attr.contentDescription
import android.R.attr.onClick
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.dropUnlessResumed
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.cinema.data.remote.ApiConstants

@Composable
fun FilmPhotos(photos: List<String>, showSnackBar: () -> Unit) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {

        Text(text = "Photos and posters", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = photos, key = { it }) { photo ->
                var dropDownMenuVisible by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .size(height = 200.dp, width = 300.dp)
                )
                {

                    AsyncImage(
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${ApiConstants.ORIGINAL_IMAGE_BASE_URL}$photo")
                            .crossfade(true)
                            .build(),
                        contentDescription = "filmPhoto",
                        contentScale = ContentScale.Crop,
                        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(modifier = Modifier.width(110.dp)) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .clickable(onClick = {
                                        dropDownMenuVisible = true
                                    }),
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "photoDropDownMenu",
                            )
                            if (dropDownMenuVisible) {
                                FilmPhotoDropDownMenu(
                                    scope = scope,
                                    showSnackBar = showSnackBar,
                                    photo = "${ApiConstants.ORIGINAL_IMAGE_BASE_URL}$photo",
                                    onDismissRequest = { dropDownMenuVisible = false })
                            }
                        }
                    }
                }
            }
        }
    }
}
