package com.example.cinema.data.remote.actors.dto

data class ActorResponse (
    val results: List<ActorModel>
)
data class ActorImagesResponse(
    val profiles: List<ActorImageModel>
)

/*
data class DetailActorResponse(
    val results: List<DetailActorModel>
)
*/
