package com.example.cinema.ui.navigation

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNavDetail(
    modifier: Modifier = Modifier.alpha(0.5f),
    openMenuClick: () -> Unit, sortByPopularityClick: () -> Unit,
    sortByUserRatingClick: () -> Unit,
    filmViewModel: FilmViewModel
) {
    val state = filmViewModel.searchText.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
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
        //focusManager.clearFocus()
    }
    TopAppBar(
        title = {},
        windowInsets = WindowInsets.statusBars,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent.copy(0.3f)
        ),
        navigationIcon = {

            Row() {
                IconButton(
                    onClick = {
                        scope.launch {
                            isTextField = !isTextField
                            NavigationManager.navigateBack()
                        }
                    },
                    content = {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = "Back",
                            tint = Color.White
                        )
                    })
            }

        },
    )
}