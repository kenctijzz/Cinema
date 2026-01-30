package com.example.cinema.domain.usecases.films

import com.example.cinema.domain.repository.FilmRepository
import javax.inject.Inject

class DeleteFilmUserRatingUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(id: Int){
        repository.deleteFilmUserRating(id)
    }
}