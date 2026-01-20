package com.example.cinema.ui.screens.actorlist

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.core.ui.UiEvent
import com.example.cinema.ui.components.PagingDataVerticalGrid
import com.example.cinema.ui.screens.filmlist.FilmInfo

@Composable
fun ActorListScreen(
    snackbarHostState: SnackbarHostState,
    actorViewModel: ActorViewModel = hiltViewModel()
) {
    val pagedActors = actorViewModel.popularActorsList.collectAsLazyPagingItems()
    val isRefreshing =
        pagedActors.loadState.refresh is LoadState.Loading && pagedActors.itemCount > 0
    LaunchedEffect(Unit) {
        actorViewModel.snackBarEvent.collect { event ->
            if (event is UiEvent.ShowSnackBar) {
                snackbarHostState.showSnackbar(message = event.message)
            }
        }
    }
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = {
            pagedActors.refresh()
        }
    ) {
        when (pagedActors.loadState.refresh) {

            is LoadState.Loading -> {
                if (pagedActors.itemCount == 0) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is LoadState.Error -> {
                Log.e(
                    "PagingError",
                    "Причина: ",
                    (pagedActors.loadState.refresh as LoadState.Error).error
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Проблемы с доступом. Проверьте подключение к VPN",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = { pagedActors.retry() }) {
                        Text("Повторить попытку")
                    }
                }
            }

            else -> PagingDataVerticalGrid(anyPagingData = actorViewModel.popularActorsList, cellsAmount = 2) { actor ->
                ActorInfo(
                    actor = actor,
                    onLikeClick = { actorViewModel.toggleActorLike(actor) }
                )
            }
        }
    }
}