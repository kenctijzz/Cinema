package com.example.cinema.data.remote.films

import com.example.cinema.data.remote.films.dto.FilmModel
import com.example.cinema.data.remote.films.dto.FilmPhotosResponse
import com.example.cinema.data.remote.films.dto.FilmResponse
import com.example.cinema.data.remote.films.dto.FilmVideoModel
import com.example.cinema.data.remote.films.dto.FilmVideosResponse
import com.example.cinema.data.remote.films.dto.SearchResponse
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
        @Query("language") lang: String = "ru-RU",
        @Query("include_image_language") imageLang: String = "ru,en,null"
    ): FilmResponse
    @GET("search/movie")
    suspend fun searchMovies(
    @Query("query") search: String,
    @Query("api_key") apikey: String,
    @Query("page") page: Int,
    @Query("language") lang: String = "ru-RU",
    @Query("include_image_language") imageLang: String = "ru,en,null"
    ): SearchResponse
    @GET("movie/{id}")
    suspend fun getFilm(
        @Path("id") id: Int,
        @Query("api_key") apikey: String,
        @Query("language") lang: String = "ru-RU"
    ): FilmModel

    @GET("movie/{id}/videos")
    suspend fun getFilmVideos(
        @Path("id") id: Int,
        @Query("api_key") apikey: String,
        @Query("language") lang: String = "ru-RU"
    ): FilmVideosResponse

    @GET("movie/{id}/images")
    suspend fun getFilmImages(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): FilmPhotosResponse
}