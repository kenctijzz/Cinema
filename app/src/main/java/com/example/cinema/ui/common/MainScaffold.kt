package com.example.cinema.ui.common

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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinema.data.repository.SortType
import com.example.cinema.ui.navigation.AppNavigationGraph
import com.example.cinema.ui.navigation.Screen
import com.example.cinema.ui.navigation.TopAppBarNav
import com.example.cinema.ui.screens.films.filminfo.FilmDetailViewModel
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel

@Composable
fun MainScaffold(
    snackBarHostState: SnackbarHostState, openMenuClick: () -> Unit,
) {
    val filmViewModel: FilmViewModel  =  hiltViewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isDetailScreen = currentDestination?.hasRoute<Screen.FilmDetail>() == true
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = !isDetailScreen,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                TopAppBarNav(
                    openMenuClick = openMenuClick,
                    sortByPopularityClick = { filmViewModel.changeFilmsSortType(SortType.POPULARITY)},
                    sortByUserRatingClick = { filmViewModel.changeFilmsSortType(SortType.USER_RATE) },
                    filmViewModel = filmViewModel)
            }
        },
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AppNavigationGraph(
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