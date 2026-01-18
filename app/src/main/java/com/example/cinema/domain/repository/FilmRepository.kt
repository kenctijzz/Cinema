package com.example.cinema.domain.repository

import androidx.paging.PagingData
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.dto.FilmModel
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getPopularMovies(): Flow<PagingData<FilmEntity>>
    suspend fun addFilm(film: FilmEntity)
    suspend fun deleteCharacter(id: Int)
    suspend fun getFilmById(id: Int): Result<FilmEntity>

    suspend fun toggleFilmLike(id: Int)
}