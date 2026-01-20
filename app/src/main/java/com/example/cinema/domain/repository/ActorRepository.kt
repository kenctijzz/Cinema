package com.example.cinema.domain.repository

import androidx.paging.PagingData
import com.example.cinema.data.local.entities.ActorEntity
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface ActorRepository {
    fun getPopularActors(): Flow<PagingData<Actor>>
    fun getFavoriteActorsFlow(): Flow<List<Actor>>
    suspend fun getActorByIdFromLocal(id: Int): Actor?
    suspend fun getActorByIdFromRemote(id: Int): Actor
    suspend fun updateActor(actor: Actor)
}