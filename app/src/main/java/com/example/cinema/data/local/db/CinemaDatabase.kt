package com.example.cinema.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.data.local.dao.ActorDao
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.entities.ActorEntity
import com.example.cinema.data.local.entities.FilmEntity

@Database(
    entities = [FilmEntity::class, ActorEntity::class],
    version = 1 ,
    exportSchema = false
)
abstract class CinemaDatabase : RoomDatabase(){

    abstract fun filmDao() : FilmDao
    abstract fun actorDao() : ActorDao
}