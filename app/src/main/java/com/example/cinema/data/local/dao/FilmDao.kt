package com.example.cinema.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.local.entities.IdRatingPair
import com.example.cinema.data.local.entities.LikedFilmsEntity
import com.example.cinema.data.local.entities.RatedFilmsEntity
import com.example.cinema.domain.model.Film
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM films WHERE isSearchResult = 0 ORDER BY page ASC, id ASC")
    fun getPagingSource(): PagingSource<Int, FilmEntity>

    @Query("SELECT * FROM films WHERE userRating IS NOT NULL ORDER BY userRating DESC, id ASC")
    fun sortPagingByUserRating(): PagingSource<Int, FilmEntity>
    @Query("SELECT * FROM films WHERE isSearchResult = 1 AND title LIKE '%' || :search || '%' AND isSearchResult = 1 ORDER BY page ASC")
    fun searchFilmsPagingSource(search: String): PagingSource<Int, FilmEntity>

    @Query("DELETE FROM films WHERE isSearchResult = 1 AND userRating IS NULL")
    fun clearSearchFilms()
    @Query("DELETE FROM films WHERE isSearchResult = 0 AND userRating IS NULL")
    fun clearPopularFilms()
    @Query("SELECT * FROM films")
    suspend fun getAllFilms(): List<FilmEntity>

    @Query("SELECT * FROM films LIMIT 1")
    suspend fun getAnyFilm(): FilmEntity?

    @Query("DELETE FROM films")
    suspend fun clearAll()

    @Query("UPDATE films SET isFavorite =:likeStatus WHERE id =:id")
    suspend fun toggleFilmLike(likeStatus: Boolean, id: Int?)

/*    @Query("SELECT * FROM films WHERE isFavorite = 1")
    fun getAllLikedFilmsFlow(): Flow<List<FilmEntity>>*/

    @Query("SELECT * FROM liked_films WHERE isFavorite = 1")
    fun getAllLikedFilmsFlow(): Flow<List<LikedFilmsEntity>>

    @Query("SELECT id FROM liked_films WHERE isFavorite = 1")
    suspend fun getAllLikedFilms(): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikedFilm(film: LikedFilmsEntity)
    @Query("DELETE FROM liked_films WHERE id = :id")
    suspend fun deleteLikedFilm(id: Int?)

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
    suspend fun getFilmById(id: Int?): FilmEntity?

    @Query("UPDATE films SET userRating = :newRating WHERE id = :id")
    suspend fun updateFilmRating(
        newRating: Int?,
        id: Int
    )
    @Query("SELECT id, userRating FROM rated_films WHERE userRating IS NOT NULL")
    suspend fun getAllRatedFilmsId(): List<IdRatingPair>

    @Query("SELECT COUNT(*) FROM liked_films")
    fun getLikedFilmsAmount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM rated_films")
    fun getRatedFilmsAmount(): Flow<Int>

    @Query("SELECT SUM(userRating) FROM rated_films")
    fun getAllUserRatings(): Flow<Int>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFilmRatingEntities(film: RatedFilmsEntity)
    @Query("DELETE FROM rated_films WHERE id = :id")
    suspend fun deleteFilmUserRating(id: Int)
    @Query("SELECT EXISTS(SELECT 1 FROM liked_films WHERE id = :id)")
    suspend fun getFilmLikeInfo(id: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM liked_films WHERE id = :id)")
    fun getLikeInfoFlow(id: Int): Flow<Boolean>

    @Query("DELETE FROM films WHERE id = -1")
    suspend fun invalidateFilms()
}