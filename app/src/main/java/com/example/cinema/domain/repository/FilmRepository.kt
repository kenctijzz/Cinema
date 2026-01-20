package com.example.cinema.domain.repository

import androidx.paging.PagingData
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.dto.FilmModel
import com.example.cinema.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getPopularMovies(): Flow<PagingData<Film>>
    suspend fun addFilm(film: Film)
    fun getFavoriteFilmsFlow(): Flow<List<Film>>
    suspend fun getFilmByIdFromLocal(id: Int): Film?
    suspend fun getFilmByIdFromRemote(id: Int): Film
    suspend fun updateFilm(film: Film)
}