package com.example.cinema.ui.components

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.example.cinema.data.repository.SortType
import com.example.cinema.ui.common.BaseViewModel
import com.example.cinema.ui.common.VisualModels
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
import com.example.cinema.ui.utils.UiError
import com.example.cinema.ui.utils.UiLoading
import com.google.android.material.loadingindicator.LoadingIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private fun Any.toFilm() {

}


@Composable
fun <T : VisualModels> PagingDataVerticalGrid(
    anyPagingData: LazyPagingItems<T>,
    state: LazyGridState,
    forceRefresh: () -> Unit,
    searchText: String,
    paddingValues: PaddingValues,
    textSearch: String,
    sortType: SortType,
    sortTypeChangeToPopular: () -> Unit,
    content: @Composable ((T) -> Unit)

) {
    val focusManager = LocalFocusManager.current
    val refreshState = anyPagingData.loadState.refresh
    val mediatorState = anyPagingData.loadState.mediator?.refresh
    val isError = refreshState is LoadState.Error || mediatorState is LoadState.Error
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (source == NestedScrollSource.UserInput) focusManager.clearFocus()
                return Offset.Zero
            }
        }
    }
    val isOffline = anyPagingData.itemSnapshotList.items.any { it.id == 101 }


    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            state = state,
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection),
            contentPadding = PaddingValues(
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            )
        ) {
            items(
                count = anyPagingData.itemCount,
                key = anyPagingData.itemKey { it.id }
            ) { index ->
                anyPagingData[index]?.let { content(it) }
            }
            if (isOffline || anyPagingData.loadState.append is LoadState.Error || anyPagingData.loadState.refresh is LoadState.Error) {
                item(span = { GridItemSpan(2) }) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(
                            text = "Ошибка загрузки актуальных данных",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 64.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.inverseSurface,
                                    shape = RoundedCornerShape(24.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.inverseSurface
                            ),
                            onClick = { anyPagingData.refresh() }
                        ) {
                            Text("Повторить")
                        }
                    }
                }
            }
            item(span = { GridItemSpan(2) }) {

                when (val appendState = anyPagingData.loadState.append) {
                    is LoadState.Loading -> {
                        if (anyPagingData.itemCount > 0) {
                            UiLoading()
                        }
                    }

                    is LoadState.Error -> {}/*{
                        if (anyPagingData.itemCount > 0 && !isOffline) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Ошибка загрузки",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Button(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(horizontal = 64.dp)
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.inverseSurface,
                                            shape = RoundedCornerShape(24.dp)
                                        ),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = MaterialTheme.colorScheme.inverseSurface
                                    ),
                                    onClick = {
                                        Log.e("Refresh list append state", "working")
                                        anyPagingData.retry()
                                    }
                                ) {
                                    Text("Повторить")
                                }
                            }
                        }
                    }*/

                    else -> {}
                }
            }
        }

        when (val refreshState = anyPagingData.loadState.refresh) {
            is LoadState.Loading -> {
                if (anyPagingData.itemCount == 0) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }

            is LoadState.Error -> {
                if (anyPagingData.itemCount == 0 && textSearch.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Не удалось загрузить данные. Проверьте соединение и попробуйте снова",
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 64.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.inverseSurface,
                                    shape = RoundedCornerShape(24.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.inverseSurface
                            ),
                            onClick = {
                                anyPagingData.retry()
                            }
                        ) {
                            Text("Повторить")
                        }
                    }
                }
            }

            else -> {}
        }
    }
}

