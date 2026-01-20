package com.example.cinema.domain.usecases.actors

import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.repository.ActorRepository
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(
    private val repository: ActorRepository
) {
    suspend operator fun invoke(id: Int): Result<Actor>{
        return try {
            val localActorEntity = repository.getActorByIdFromLocal(id)
            if(localActorEntity != null && !localActorEntity.biography.isNullOrBlank()){
                Result.success(localActorEntity)
            }else{
                val remoteActor = repository.getActorByIdFromRemote(id)
                repository.updateActor(remoteActor)
                Result.success(remoteActor)
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}