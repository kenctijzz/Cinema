package com.example.cinema.di

import com.example.cinema.data.remote.ApiConstants
import com.example.cinema.data.remote.FilmApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @ApiKey
    fun provideApiKey(): String {
        return ApiConstants.API_KEY
    }

    @Provides
    fun provideFilmApi(): FilmApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmApi::class.java)
    }
}
