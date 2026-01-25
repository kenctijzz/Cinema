package com.example.cinema.domain.repository

import androidx.paging.PagingData
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.model.FilmVideo
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getPopularMovies(): Flow<PagingData<Film>>
    fun getFavoriteFilmsFlow(): Flow<List<Film>>
    fun getFilmFlow(id: Int): Flow<Film>
    suspend fun getFilmByIdFromLocal(id: Int): Film?
    suspend fun getFilmByIdFromRemote(id: Int): Film
    suspend fun updateFilm(film: Film)
    suspend fun toggleFilmLike(likeStatus: Boolean, id: Int)
/*    suspend fun getFilmVideos(id: Int): List<FilmVideo>*/
    suspend fun updateFilmRating(id: Int, newRating: Int)
}