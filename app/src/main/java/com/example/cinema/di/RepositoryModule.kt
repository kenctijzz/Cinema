package com.example.cinema.di

import com.example.cinema.data.repository.FilmRepositoryImpl
import com.example.cinema.domain.repository.FilmRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun getCharacterRepository(
        characterRepositoryImpl: FilmRepositoryImpl
    ): FilmRepository
}