package com.example.cinema.domain.usecases.actors

import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.ActorRepository
import com.example.cinema.domain.repository.FilmRepository
import javax.inject.Inject

class ToggleActorLikeUseCase @Inject constructor(
    private val repository: ActorRepository
) {
    suspend operator fun invoke(actor: Actor): Boolean {
        val newFavoriteStatus = !actor.isFavorite
        val updatedActor = actor.copy(isFavorite = newFavoriteStatus)
        repository.updateActor(updatedActor)
        return newFavoriteStatus
    }
}