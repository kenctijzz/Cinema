package com.example.cinema.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.local.entities.IdRatingPair
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM films ORDER BY page ASC, id ASC")
    fun getPagingSource(): PagingSource<Int, FilmEntity>

    @Query("SELECT * FROM films WHERE userRating IS NOT NULL ORDER BY userRating DESC, id ASC")
    fun sortPagingByUserRating(): PagingSource<Int, FilmEntity>

    @Query("SELECT * FROM films LIMIT 1")
    suspend fun getAnyFilm(): FilmEntity?

    @Query("DELETE FROM films")
    suspend fun clearAll()

    @Query("UPDATE films SET isFavorite =:likeStatus WHERE id =:id")
    suspend fun toggleFilmLike(likeStatus: Boolean, id: Int?)

    @Query("SELECT * FROM films WHERE isFavorite = 1")
    fun getAllLikedFilmsFlow(): Flow<List<FilmEntity>>

    @Query("SELECT id FROM films WHERE isFavorite = 1")
    suspend fun getAllLikedFilms(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilm(film: FilmEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInitialFilm(film: FilmEntity): Long

    @Query("UPDATE films SET runtime = :runtime, video = :video, photos = :photos WHERE id = :id")
    suspend fun updateFilmDetails(
        runtime: Int,
        id: Int,
        video: String?,
        photos: List<String>,
    )

    @Query("SELECT * FROM films WHERE id = :id")
    fun getFilmFlow(id: Int): Flow<FilmEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films: List<FilmEntity>)

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun getFilmById(id: Int): FilmEntity?

    @Query("UPDATE films SET userRating = :newRating WHERE id = :id")
    suspend fun updateFilmRating(
        newRating: Int,
        id: Int
    )

    @Query("SELECT id, userRating FROM films WHERE userRating IS NOT NULL")
    suspend fun getAllRatedFilmsId(): List<IdRatingPair>
}