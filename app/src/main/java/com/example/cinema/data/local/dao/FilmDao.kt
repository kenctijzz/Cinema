package com.example.cinema.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema.data.local.entities.FilmEntity

@Dao
interface FilmDao {
    @Query("SELECT * FROM films")
    suspend fun getDataPopularFilms(): List<FilmEntity>

    @Query("DELETE FROM films WHERE id = :id")
    suspend fun deleteCharacter(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFilm(film: FilmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(films: List<FilmEntity>)

    @Query("SELECT * FROM films WHERE id = :id")
    suspend fun getFilmById(id: Int): FilmEntity?
}