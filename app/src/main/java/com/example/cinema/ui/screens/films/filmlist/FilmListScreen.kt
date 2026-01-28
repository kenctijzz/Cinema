package com.example.cinema.ui.screens.films.filmlist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.data.repository.SortType
import com.example.cinema.ui.components.PagingDataVerticalGrid
import com.example.cinema.ui.utils.UiError
import com.example.cinema.ui.utils.UiLoading
import kotlinx.coroutines.delay

@Composable
fun FilmListScreen(
    filmViewModel: FilmViewModel,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues
) {
    val focusManager = LocalFocusManager.current
    val sortType = filmViewModel.filmsSortType.collectAsStateWithLifecycle()
    val searchText = filmViewModel.searchText.collectAsStateWithLifecycle()
    val pagedMovies = filmViewModel.filmsFlow.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()
    val currentSort by filmViewModel.filmsSortType.collectAsState()
    var isFirstComposition by remember { mutableStateOf(true) }
    LaunchedEffect(currentSort) {
        if (isFirstComposition) {
            isFirstComposition = false
        } else {
            gridState.animateScrollToItem(0)
            delay(50)
            gridState.animateScrollToItem(0)
        }
    }
    val isRefreshing =
        pagedMovies.loadState.refresh is LoadState.Loading && pagedMovies.itemCount > 0
    LaunchedEffect(Unit) {
        filmViewModel.snackBarEvent.collect { event ->
            if (event is UiEvent.ShowSnackBar) {
                snackbarHostState.showSnackbar(message = event.message)
            }
        }
    }

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = {
            focusManager.clearFocus()
            filmViewModel.searchTextChange("")
            pagedMovies.refresh()
        }
    ) {
        val mediatorRefreshState = pagedMovies.loadState.mediator?.refresh
        val localRefreshState = pagedMovies.loadState.refresh
        val isRefreshing = mediatorRefreshState is LoadState.Loading
        var showErrorWithDelay by remember { mutableStateOf(false) }
        val shouldShowError = (mediatorRefreshState is LoadState.Error ||
                (localRefreshState is LoadState.NotLoading && pagedMovies.itemCount == 0)) &&
                searchText.value.isEmpty() &&
                sortType.value == SortType.POPULARITY &&
                mediatorRefreshState !is LoadState.Loading
        LaunchedEffect(shouldShowError) {
            if (shouldShowError) {
                delay(200)
                showErrorWithDelay = true
            } else {
                showErrorWithDelay = false
            }
        }
        if(showErrorWithDelay){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Не удалось загрузить данные. Проверьте соединение и попробуйте снова.",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
                Button(
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.inverseSurface,
                        RoundedCornerShape(24.dp)
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.inverseSurface
                    ), onClick = { pagedMovies.refresh() }) {
                    Text("Повторить")
                }
            }
        }
        when {
            (localRefreshState is LoadState.Loading || mediatorRefreshState is LoadState.Loading)
                    && pagedMovies.itemCount == 0 -> {
                UiLoading()
            }

            else -> {
                PagingDataVerticalGrid(
                    anyPagingData = pagedMovies,
                    state = gridState,
                    sortType = sortType.value,
                    searchText = searchText.value,
                    paddingValues = paddingValues,
                    forceRefresh = { filmViewModel.manualRefresh() },
                    textSearch = searchText.value
                ) { film ->
                    FilmInfo(
                        film = film,
                        viewModel = filmViewModel,
                        onLikeClick = { filmViewModel.toggleFilmLike(film) }
                    )
                }
            }
        }
    }
}