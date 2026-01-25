package com.example.cinema.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNav(openMenuClick: () -> Unit) {
    val state = remember { TextFieldState("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isTextField by remember { mutableStateOf(false) }
    LaunchedEffect(isTextField) {
        focusRequester.requestFocus()
    }
    BackHandler(enabled = isTextField) {
        isTextField = !isTextField
        focusManager.clearFocus()
    }
    TopAppBar(
        title = {},
        windowInsets = WindowInsets.statusBars,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent.copy(0.3f)
        ),
        navigationIcon = {
            if (isTextField) {
                Row() {
                    IconButton(
                        onClick = { focusManager.clearFocus()
                            isTextField = !isTextField},
                        content = {
                            Icon(
                                Icons.Default.ArrowBack, contentDescription = "Back",
                                tint = Color.White
                            )
                        })
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth().padding(horizontal = 8.dp)
                            .focusRequester(focusRequester)
                            .background(color = Color.Transparent.copy(0.65f)),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        state = state,
                        placeholder = { Text("Search") },
                    )
                }
            } else {
                IconButton(
                    onClick = { openMenuClick() },
                    content = {
                        Icon(
                            Icons.Default.Menu, contentDescription = "OpenMenu",
                            tint = Color.White
                        )
                    })
            }
        },
        actions = {
            Row() {
                if (!isTextField) {
                    IconButton(
                        onClick = { isTextField = !isTextField },
                        content = {
                            Icon(
                                Icons.Default.Search, contentDescription = "Search",
                                tint = Color.White
                            )
                        })
                    IconButton(
                        onClick = {},
                        content = {
                            Icon(
                                Icons.AutoMirrored.Filled.List, contentDescription = "Sort",
                                tint = Color.White
                            )
                        })
                }
            }
        }
    )
}