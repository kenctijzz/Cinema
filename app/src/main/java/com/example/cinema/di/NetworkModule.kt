package com.example.cinema.di

import android.os.Build
import com.example.cinema.BuildConfig
import com.example.cinema.data.remote.ApiConstants
import com.example.cinema.data.remote.FilmApi
import com.google.gson.internal.GsonBuildConfig
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
        return BuildConfig.API_KEY
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
