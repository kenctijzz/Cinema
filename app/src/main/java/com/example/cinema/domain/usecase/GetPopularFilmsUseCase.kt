package com.example.cinema.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.dto.FilmModel
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetPopularFilmsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<PagingData<Film>>{
        return repository.getPopularMovies()
    }
}