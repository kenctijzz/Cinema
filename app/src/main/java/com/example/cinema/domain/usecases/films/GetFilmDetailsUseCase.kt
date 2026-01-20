package com.example.cinema.domain.usecases.films

import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import javax.inject.Inject


class GetFilmDetailsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(id: Int): Result<Film> {
        return try {
            val localFilmEntity = repository.getFilmByIdFromLocal(id)
            if (localFilmEntity != null) {
                Result.success(localFilmEntity)
            } else {
                val remoteFilm = repository.getFilmByIdFromRemote(id)
                repository.updateFilm(remoteFilm)
                Result.success(remoteFilm)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}