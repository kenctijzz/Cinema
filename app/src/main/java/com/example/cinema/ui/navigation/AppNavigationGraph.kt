package com.example.cinema.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinema.core.navigation.UiNavigation
import com.example.cinema.ui.screens.actors.actorinfo.ActorDetailScreen
import com.example.cinema.ui.screens.actors.actorlist.ActorListScreen
import com.example.cinema.ui.screens.films.favoritefilms.FavoriteFilms
import com.example.cinema.ui.screens.films.filminfo.FilmDetailScreen
import com.example.cinema.ui.screens.films.filmlist.FilmListScreen

@Composable
fun AppNavigationGraph(snackBarHostState: SnackbarHostState, navController: NavHostController) {
   // val navController = rememberNavController()

    LaunchedEffect(Unit) {
        NavigationManager.events.collect { event ->
            val currentState = navController.currentBackStackEntry?.lifecycle?.currentState
            if (currentState == Lifecycle.State.RESUMED) {
                when (event) {
                    is UiNavigation.NavigateTo -> {
                        navController.navigate(event.route) { launchSingleTop = true }
                    }

                    is UiNavigation.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screen.FilmList,
        enterTransition = { fadeIn(animationSpec = tween(100)) },
        exitTransition = { fadeOut(animationSpec = tween(100)) },
        popEnterTransition = { fadeIn(animationSpec = tween(100)) },
        popExitTransition = { fadeOut(animationSpec = tween(100)) }
    ) {
        composable<Screen.FilmList> {
            FilmListScreen(snackbarHostState = snackBarHostState)
        }
        composable<Screen.FilmDetail> {
            FilmDetailScreen(snackbarHostState = snackBarHostState)
        }
        composable<Screen.FavoriteFilms> {
            FavoriteFilms(snackbarHostState = snackBarHostState)
        }
        composable<Screen.ActorList> {
            ActorListScreen(snackbarHostState = snackBarHostState)
        }
        composable<Screen.ActorDetail> {
            ActorDetailScreen()
        }
    }
}