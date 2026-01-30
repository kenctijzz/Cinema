package com.example.cinema.ui.navigation

import android.R.attr.singleLine
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNav(
    modifier: Modifier = Modifier.alpha(0.5f),
    openMenuClick: () -> Unit, sortByPopularityClick: () -> Unit,
    sortByUserRatingClick: () -> Unit,
    filmViewModel: FilmViewModel,
) {
    val state by filmViewModel.searchText.collectAsStateWithLifecycle()
    var textFieldValue by remember { mutableStateOf(TextFieldValue(state)) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isTextField by remember { mutableStateOf(false) }
    var sortDropDown by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(text = state,
            selection = TextRange(state.length))
        }
    }
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
        filmViewModel.searchTextChange("")
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
                            filmViewModel.searchTextChange("")
                        },
                        content = {
                            Icon(
                                Icons.Default.ArrowBack, contentDescription = "Back",
                                tint = Color.White
                            )
                        })
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { newText ->
                            textFieldValue = newText
                            filmViewModel.searchTextChange(newText.text)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    textFieldValue = textFieldValue.copy(
                                        selection = TextRange(textFieldValue.text.length)
                                    )
                                }
                            }
                            .background(color = Color.Transparent.copy(0.65f)),
                        singleLine = true,
                        placeholder = { Text("Поиск") }
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