package com.example.cinema.domain.usecases.films

import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManualRefreshUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke() {
         repository.manualRefresh()
    }
}