package com.example.cinema.data.remote.films

import com.example.cinema.data.remote.films.dto.FilmModel
import com.example.cinema.data.remote.films.dto.FilmPhotosResponse
import com.example.cinema.data.remote.films.dto.FilmResponse
import com.example.cinema.data.remote.films.dto.FilmVideoModel
import com.example.cinema.data.remote.films.dto.FilmVideosResponse
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
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

    @GET("movie/{id}/videos")
    suspend fun getFilmVideos(
        @Path("id") id: Int,
        @Query("api_key") apikey: String
    ): FilmVideosResponse

    @GET("movie/{id}/images")
    suspend fun getFilmImages(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): FilmPhotosResponse
}