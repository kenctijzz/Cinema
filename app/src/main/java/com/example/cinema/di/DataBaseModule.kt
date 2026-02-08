package com.example.cinema.di

import android.content.Context
import androidx.room.Room
import com.example.cinema.data.local.dao.ActorDao
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.db.CinemaDatabase
import com.example.cinema.data.local.db.MIGRATION_2_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CinemaDatabase {
        return Room.databaseBuilder(
            context, CinemaDatabase::class.java, "film_database"
        ).addMigrations(MIGRATION_2_3)
            .build()
    }

    @Provides
    @Singleton
    fun provideActorDao(database: CinemaDatabase): ActorDao {
        return database.actorDao()
    }

    @Provides
    @Singleton
    fun provideFilmDao(database: CinemaDatabase): FilmDao {
        return database.filmDao()
    }
}