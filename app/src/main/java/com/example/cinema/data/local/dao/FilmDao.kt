package com.example.cinema.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema.data.local.entities.FilmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM films ORDER BY page ASC, id ASC")
    fun getPagingSource(): PagingSource<Int, FilmEntity>

    @Query("SELECT * FROM films LIMIT 1")
    suspend fun getAnyFilm(): FilmEntity?

    @Query("DELETE FROM films")
    suspend fun clearAll()

    @Query("UPDATE films SET isFavorite = 1 WHERE id =:id")
    suspend fun loadLikesAfterRefresh(id: Int)

    @Query("UPDATE films SET isFavorite =:likeStatus WHERE id =:id")
    suspend fun toggleFilmLike(likeStatus: Boolean, id: Int)

    @Query("SELECT * FROM films WHERE isFavorite = 1")
    fun getAllLikedFilmsFlow(): Flow<List<FilmEntity>>

    @Query("SELECT id FROM FILMS WHERE isFavorite = 1")
    suspend fun getAllLikedFilms(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilm(film: FilmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films: List<FilmEntity>)

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun getFilmById(id: Int): FilmEntity?
}