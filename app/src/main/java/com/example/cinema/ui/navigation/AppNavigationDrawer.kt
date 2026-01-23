package com.example.cinema.ui.navigation

import android.R.attr.label
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinema.core.navigation.UiNavigation
import kotlinx.coroutines.launch

@Composable
fun AppNavigationDrawer(snackBarHostState: SnackbarHostState) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                NavigationDrawerItem(
                    label = { Text(text = "Лучшие Фильмы") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            NavigationManager.navigateTo(Screen.FilmList)
                        }
                    },
                    icon = { Icon(Icons.Default.Album, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Лучшие Актеры") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            NavigationManager.navigateTo(Screen.ActorList)
                        }
                    },
                    icon = { Icon(Icons.Default.People, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Любимые Фильмы") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            NavigationManager.navigateTo(Screen.FavoriteFilms)
                        }
                    },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { innerPadding ->

                AppNavigationGraph(snackBarHostState = snackBarHostState)


        }
    }
}