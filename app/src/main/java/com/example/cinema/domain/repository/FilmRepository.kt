package com.example.cinema.domain.repository

import androidx.paging.PagingData
import com.example.cinema.data.local.entities.LikedFilmsEntity
import com.example.cinema.data.repository.SortType
import com.example.cinema.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getFilms(sortType: SortType, search: String): Flow<PagingData<Film>>
    //fun getUserRateSortFilms(): Flow<PagingData<Film>>
    fun getFavoriteFilmsFlow(): Flow<List<Film>>
    fun getFilmFlow(id: Int): Flow<Film?>
    suspend fun getFilmByIdFromLocal(id: Int): Film?
    suspend fun getFilmByIdFromRemote(id: Int): Film
    suspend fun updateFilm(film: Film)
    suspend fun toggleFilmLike(likeStatus: Boolean, id: Int?)
/*    suspend fun getFilmVideos(id: Int): List<FilmVideo>*/
    suspend fun updateFilmRating(id: Int, newRating: Int)
    suspend fun getLikeInfoById(id: Int): Boolean
    fun getLikedFilmsAmount() : Flow<Int>
    fun getRatedFilmsAmount(): Flow<Int>
    fun getAllUserRatingsSum(): Flow<Int>
    suspend fun manualRefresh()
}