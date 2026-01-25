package com.example.cinema.domain.usecases.films

import com.example.cinema.domain.repository.FilmRepository
import javax.inject.Inject

class UpdateFilmRatingUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(newRating: Int, id: Int) {
        repository.updateFilmRating(id, newRating)
    }
}