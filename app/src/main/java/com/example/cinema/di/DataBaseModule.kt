package com.example.cinema.di

import android.content.Context
import androidx.room.Room
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.db.FilmDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): FilmDatabase {
        return Room.databaseBuilder(
            context, FilmDatabase::class.java, "film_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(database: FilmDatabase): FilmDao {
        return database.filmDao()
    }
}