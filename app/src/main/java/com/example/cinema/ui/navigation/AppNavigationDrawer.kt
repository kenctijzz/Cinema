package com.example.cinema.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.data.repository.SortType
import com.example.cinema.ui.common.MainScaffold
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigationDrawer(snackBarHostState: SnackbarHostState) {
    val filmViewModel: FilmViewModel = hiltViewModel()
    val focusManager = LocalFocusManager.current
    val sortType = filmViewModel.filmsSortType.collectAsStateWithLifecycle()
    val searchText = filmViewModel.searchText.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()
    val currentSort by filmViewModel.filmsSortType.collectAsState()
    var isFirstComposition by remember { mutableStateOf(true) }
    val pagedMovies = filmViewModel.filmsFlow.collectAsLazyPagingItems()
    val mediatorRefreshState = pagedMovies.loadState.mediator?.refresh
    val localRefreshState = pagedMovies.loadState.refresh
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                windowInsets = WindowInsets.statusBars,
                drawerContainerColor = MaterialTheme.colorScheme.background.copy(0.98f)
            ) {
                NavigationDrawerItem(
                    label = { Text(text = "Список фильмов") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            filmViewModel.changeFilmsSortType(SortType.POPULARITY)
                            filmViewModel.searchTextChange("")
                            drawerState.close()
                            pagedMovies.refresh()
                            NavigationManager.navigateTo(Screen.FilmList)
                        }
                    },
                    icon = { Icon(Icons.Default.Album, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Любимые") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            NavigationManager.navigateTo(Screen.FavoriteFilms)
                        }
                    },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Мой профиль") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            filmViewModel.searchTextChange("")
                            drawerState.close()
                            NavigationManager.navigateTo(Screen.UserStats)
                        }
                    },
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = null) }
                )
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            MainScaffold(
                filmViewModel = filmViewModel,
                snackBarHostState = snackBarHostState,
                openMenuClick = { scope.launch { drawerState.open() } })
        }
    }
}