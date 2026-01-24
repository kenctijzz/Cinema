package com.example.cinema.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.People
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
            ) { innerPadding ->

                AppNavigationGraph(snackBarHostState = snackBarHostState)
            }
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