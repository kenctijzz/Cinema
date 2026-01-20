package com.example.cinema.domain.usecase

import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private fun FilmEntity.toDomainModel(pageNumber: Int): Film {
    return Film(
        id = this.id,
        title = this.title,
        image = this.image,
        releaseDate = this.releaseDate,
        adult = this.adult,
        overview = this.overview,
        isFavorite = this.isFavorite,
        page = pageNumber
    )
}

class GetFavoriteFilmsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<List<Film>> {
        return repository.getFavoriteFilmsFlow()
    }
}
