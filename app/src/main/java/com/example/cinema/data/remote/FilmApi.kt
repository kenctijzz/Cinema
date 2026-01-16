package com.example.cinema.data.remote

import com.example.cinema.data.remote.dto.FilmModel
import com.example.cinema.data.remote.dto.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ): FilmResponse

    @GET("3/movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): FilmModel
}