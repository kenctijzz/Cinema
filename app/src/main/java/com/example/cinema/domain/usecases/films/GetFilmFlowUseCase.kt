package com.example.cinema.domain.usecases.films

import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import javax.inject.Inject

class GetFilmFlowUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(id: Int): Flow<Film?> {
        return repository.getFilmFlow(id)

    }
}