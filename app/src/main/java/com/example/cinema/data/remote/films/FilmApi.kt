package com.example.cinema.data.remote.films

import com.example.cinema.data.remote.films.dto.FilmModel
import com.example.cinema.data.remote.films.dto.FilmPhotosResponse
import com.example.cinema.data.remote.films.dto.FilmResponse
import com.example.cinema.data.remote.films.dto.FilmVideoModel
import com.example.cinema.data.remote.films.dto.FilmVideosResponse
import com.example.cinema.data.remote.films.dto.SearchResponse
import com.example.cinema.ui.screens.films.filmlist.FilmViewModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("api/v2.2/films/collections")
    suspend fun getPopularMovies(
        @Header("X-API-KEY") apikey: String,
        @Query("page") page: Int,
        @Query("type") type: String = "TOP_POPULAR_MOVIES",
        @Query("language") lang: String = "ru-RU",
        @Query("include_image_language") imageLang: String = "ru,en,null"
    ): FilmResponse
    @GET("api/v2.1/films/search-by-keyword")
    suspend fun searchMovies(
    @Query("keyword") search: String,
    @Header("X-API-KEY") apikey: String,
    @Query("page") page: Int,
    @Query("language") lang: String = "ru-RU",
    @Query("include_image_language") imageLang: String = "ru,en,null"
    ): SearchResponse
    @GET("api/v2.2/films/{id}")
    suspend fun getFilm(
        @Path("id") id: Int,
        @Header("X-API-KEY") apikey: String,
        @Query("language") lang: String = "ru-RU"
    ): FilmModel

    @GET("api/v2.2/films/{id}/videos")
    suspend fun getFilmVideos(
        @Path("id") id: Int,
        @Header("X-API-KEY") apikey: String,
        @Query("language") lang: String = "ru-RU"
    ): FilmVideosResponse

    @GET("api/v2.2/films/{id}/images")
    suspend fun getFilmImages(
        @Path("id") id: Int,
        @Query("type") type: String = "STILL",
        @Header("X-API-KEY") apikey: String
    ): FilmPhotosResponse
}