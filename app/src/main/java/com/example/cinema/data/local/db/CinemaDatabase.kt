package com.example.cinema.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cinema.data.local.dao.ActorDao
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.entities.ActorEntity
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.local.entities.LikedFilmsEntity
import com.example.cinema.data.local.entities.RatedFilmsEntity
import com.example.cinema.data.remote.JsonConverters

@Database(
    entities = [FilmEntity::class, ActorEntity::class, LikedFilmsEntity::class, RatedFilmsEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(JsonConverters::class)
abstract class CinemaDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao
    abstract fun actorDao(): ActorDao
}