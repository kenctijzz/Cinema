package com.example.cinema.ui.screens.films.filminfo.components

import android.R.attr.maxLines
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun FilmOverview(overview: String) {
    var onFullOverview by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var isExpandable by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    )
    {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = 1f
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()
                    if (!onFullOverview && isExpandable) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                0.5f to Color.Black,
                                1.0f to Color.Transparent
                            ),
                            blendMode = BlendMode.DstIn
                        )
                    }
                },
            text = if(overview != "")overview else "У фильма отсутствует описание",
            textAlign = TextAlign.Start,
            maxLines = if (onFullOverview) 100 else 3,
            overflow = TextOverflow.Clip,
            onTextLayout = { result ->
                if (!onFullOverview) {
                    isExpandable = result.hasVisualOverflow
                }
            }
        )
        if (isExpandable)
            Text(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .clickable(onClick = { onFullOverview = !onFullOverview }),
                text = if (onFullOverview) "Свернуть" else "Развернуть описание",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF36621)
            )
    }
}





