package com.example.cinema.ui.navigation

import android.R.attr.enabled
import android.R.attr.text
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNav(
    openMenuClick: () -> Unit, sortByPopularityClick: () -> Unit,
    sortByUserRatingClick: () -> Unit,
    filmViewModel: FilmViewModel
) {
    val state = filmViewModel.searchText.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isTextField by remember { mutableStateOf(false) }
    var sortDropDown by remember { mutableStateOf(false) }
    if (sortDropDown) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            TopBarDropMenu(
                sortByPopularityClick = { sortByPopularityClick() },
                sortByUserRatingClick = { sortByUserRatingClick() },
                onDismiss = { sortDropDown = !sortDropDown },
                filmViewModel = filmViewModel
            )
        }
    }
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
                        onClick = {
                            focusManager.clearFocus()
                            isTextField = !isTextField
                        },
                        content = {
                            Icon(
                                Icons.Default.ArrowBack, contentDescription = "Back",
                                tint = Color.White
                            )
                        })
                    OutlinedTextField(
                        value = state.value,
                        onValueChange = { newText ->
                            filmViewModel.searchTextChange(newText)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .focusRequester(focusRequester)
                            .background(color = Color.Transparent.copy(0.65f)),
                        singleLine = true,
                        placeholder = { Text("Search") }
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
            Row(modifier = Modifier.padding(16.dp)) {
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
                        onClick = { sortDropDown = !sortDropDown },
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