package com.example.cinema.domain.usecases.films

import androidx.paging.PagingData
import com.example.cinema.data.repository.SortType
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetFilmsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(sortType: SortType): Flow<PagingData<Film>> {
        return repository.getFilms(sortType)
    }
}