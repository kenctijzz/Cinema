package com.example.cinema.data.remote.actors

import com.example.cinema.data.remote.actors.dto.ActorModel
import com.example.cinema.data.remote.actors.dto.ActorResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorApi {
    @GET("person/popular")
    suspend fun getPopularActors(
        @Query("api_key") apikey: String,
        @Query("page") page: Int
    ): ActorResponse

    @GET("person/{id}")
    suspend fun getActor(@Path("id") id: Int): ActorModel
}