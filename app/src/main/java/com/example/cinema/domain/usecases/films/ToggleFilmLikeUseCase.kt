package com.example.cinema.domain.usecases.films

import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import javax.inject.Inject

class ToggleFilmLikeUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(film: Film): Boolean {
        val newFavoriteStatus = !film.isFavorite
        repository.toggleFilmLike(newFavoriteStatus, film.id)
        return newFavoriteStatus
    }
}
