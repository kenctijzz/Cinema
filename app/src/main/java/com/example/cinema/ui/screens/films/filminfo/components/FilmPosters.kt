package com.example.cinema.ui.screens.films.filminfo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun FilmPosters(posters: List<String>, showSnackBar: () -> Unit) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {


        if (posters.isNotEmpty()) {
            Text(text = "Постеры", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = posters, key = { it }) { poster ->
                    var dropDownMenuVisible by remember { mutableStateOf(false) }

                    Box(
                        modifier = Modifier
                            .height(height = 460.dp)
                            .width(330.dp)
                    )
                    {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(poster)
                                .crossfade(true)
                                .build(),
                            contentDescription = "filmPoster",
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
                                    contentDescription = "posterDropDownMenu",
                                )
                                if (dropDownMenuVisible) {
                                    FilmPhotoDropDownMenu(
                                        scope = scope,
                                        showSnackBar = showSnackBar,
                                        item = poster,
                                        onDismissRequest = { dropDownMenuVisible = false })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
