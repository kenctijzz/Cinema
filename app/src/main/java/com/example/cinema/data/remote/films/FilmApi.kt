package com.example.cinema.data.remote.films

import com.example.cinema.data.remote.films.dto.FilmModel
import com.example.cinema.data.remote.films.dto.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ): FilmResponse

    @GET("movie/{id}")
    suspend fun getFilm(
        @Path("id") id: Int,
        @Query("api_key") apikey: String
    ): FilmModel
}