package com.example.cinema.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.entities.FilmEntity

@Database(
    entities = [FilmEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmDatabase : RoomDatabase(){

    abstract fun filmDao() : FilmDao
}