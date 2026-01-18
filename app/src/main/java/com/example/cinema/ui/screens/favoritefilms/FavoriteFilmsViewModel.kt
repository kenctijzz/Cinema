package com.example.cinema.ui.screens.favoritefilms

import androidx.lifecycle.ViewModel
import com.example.cinema.data.repository.FilmRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteFilmsViewModel @Inject constructor(
    private val repositoryImpl: FilmRepositoryImpl
): ViewModel() {

}