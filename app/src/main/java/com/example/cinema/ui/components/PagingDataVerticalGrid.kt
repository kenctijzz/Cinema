package com.example.cinema.ui.components

import android.R.attr.type
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.cinema.domain.model.Film
import com.example.cinema.ui.common.VisualModels
import com.example.cinema.ui.screens.filmlist.FilmInfo
import com.example.cinema.ui.screens.filmlist.FilmViewModel
import kotlinx.coroutines.flow.Flow

private fun Any.toFilm() {

}


@Composable
fun <T : VisualModels> PagingDataVerticalGrid
            (anyPagingData: Flow<PagingData<T>>,
             cellsAmount: Int,
            content: @Composable (T) -> Unit

){
        val pagedItems = anyPagingData.collectAsLazyPagingItems()
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(cellsAmount)) {
            items(
                count = pagedItems.itemCount,
                key = pagedItems.itemKey { it.id })
            { index ->

                val item = pagedItems[index]

                item?.let {
                    content(item)
                }
            }
            when (pagedItems.loadState.append) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(cellsAmount) }) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item(span = { GridItemSpan(cellsAmount) }) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Ошибка загрузки", color = MaterialTheme.colorScheme.error)
                            Button(onClick = { pagedItems.retry() }) {
                                Text("Повторить")
                            }
                        }

                    }
                }

                else -> {}
            }
        }

    }
