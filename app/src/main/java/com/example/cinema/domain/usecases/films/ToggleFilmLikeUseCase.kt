package com.example.cinema.domain.usecases.films

import android.util.Log
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import javax.inject.Inject

class ToggleFilmLikeUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(film: Film?): Boolean {
        if (film == null) return false
        Log.e("toogkelikeinfo", "$film")
        val newFavoriteStatus = !film.isFavorite
        Log.e("toogkelikeinfo", "$newFavoriteStatus")
        repository.toggleFilmLike(newFavoriteStatus, film)
        Log.e("toogkelikeinfo", "${film.isFavorite}")
        return newFavoriteStatus
    }
}
