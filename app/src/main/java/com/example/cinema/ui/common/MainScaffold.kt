package com.example.cinema.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cinema.data.repository.SortType
import com.example.cinema.ui.navigation.AppNavigationGraph
import com.example.cinema.ui.navigation.Screen
import com.example.cinema.ui.navigation.TopAppBarNav
import com.example.cinema.ui.navigation.TopAppBarNavDetail
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel

@Composable
fun MainScaffold(
    snackBarHostState: SnackbarHostState, openMenuClick: () -> Unit,
    filmViewModel: FilmViewModel
) {
    val searchText = filmViewModel.searchText.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val focusManager = LocalFocusManager.current
    val sortType = filmViewModel.filmsSortType.collectAsStateWithLifecycle()
    val pagedMovies = filmViewModel.filmsFlow.collectAsLazyPagingItems()
    val mediatorRefreshState = pagedMovies.loadState.mediator?.refresh
    val localRefreshState = pagedMovies.loadState.refresh
    val gridState = rememberLazyGridState()
    val currentSort by filmViewModel.filmsSortType.collectAsState()
    var isFirstComposition by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isDetailScreen = currentDestination?.hasRoute<Screen.FilmDetail>() == true
    val isMainScreen = currentDestination?.hasRoute<Screen.FilmList>() == true
    BackHandler(enabled = searchText.value != ""){
        filmViewModel.changeFilmsSortType(SortType.POPULARITY)
        filmViewModel.searchTextChange("")
    }
    Scaffold(

        topBar = {
            if(isMainScreen) {
                TopAppBarNav(
                    openMenuClick = openMenuClick,
                    sortByPopularityClick = { filmViewModel.changeFilmsSortType(SortType.POPULARITY) },
                    sortByUserRatingClick = { filmViewModel.changeFilmsSortType(SortType.USER_RATE) },
                    filmViewModel = filmViewModel
                )
            }
            else{
                TopAppBarNavDetail(
                    openMenuClick = openMenuClick,
                    sortByPopularityClick = { filmViewModel.changeFilmsSortType(SortType.POPULARITY) },
                    sortByUserRatingClick = { filmViewModel.changeFilmsSortType(SortType.USER_RATE) },
                    filmViewModel = filmViewModel
                )
            }
        },
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AppNavigationGraph(
                paddingValues = innerPadding,
                snackBarHostState = snackBarHostState,
                navController = navController,
                filmViewModel = filmViewModel
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
                    .background(color = MaterialTheme.colorScheme.background.copy(0.3f))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsBottomHeight(WindowInsets.navigationBars)
                    .align(Alignment.BottomStart)
                    .background(color = MaterialTheme.colorScheme.background.copy(0.3f))
            )
        }

    }
}