package com.example.cinema.domain.usecases.actors

import androidx.paging.PagingData
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularActorsUseCase @Inject constructor(
    private val repository: ActorRepository
) {
    operator fun invoke(): Flow<PagingData<Actor>>{
        return repository.getPopularActors()
    }
}