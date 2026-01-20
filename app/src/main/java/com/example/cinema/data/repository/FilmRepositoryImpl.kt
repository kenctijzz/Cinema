package com.example.cinema.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.db.FilmDatabase
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.FilmApi
import com.example.cinema.data.remote.dto.FilmModel
import com.example.cinema.data.repository.paging.FilmRemoteMediator
import com.example.cinema.di.ApiKey
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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

private fun FilmModel.toDomainModel(pageNumber: Int): Film {
    return Film(
        id = this.id,
        title = this.title,
        image = this.image,
        releaseDate = this.releaseDate,
        adult = this.adult,
        overview = this.overview,
        isFavorite = false,
        page = pageNumber
    )
}

fun Film.toEntity(pageNumber: Int): FilmEntity {
    return FilmEntity(
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

class FilmRepositoryImpl @Inject constructor(
    private val filmApi: FilmApi,
    private val filmDao: FilmDao,
    private val db: FilmDatabase,
    @param:ApiKey private val apiKey: String
) :
    FilmRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = false, initialLoadSize = 20,
            ),
            remoteMediator = FilmRemoteMediator(filmApi, db, apiKey),
            pagingSourceFactory = { db.filmDao().getPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomainModel(pageNumber = entity.page) }
        }
    }

    override suspend fun addFilm(film: Film) {
        filmDao.addFilm(film.toEntity(pageNumber = film.page))
    }

    override suspend fun getFilmByIdFromLocal(id: Int): Film {
        val film = filmDao.getFilmById(id)
            ?: throw Exception("Film Not Found")
        return film.toDomainModel(pageNumber = film.page)
    }

    override suspend fun getFilmByIdFromRemote(id: Int): Film {
        val film = filmApi.getFilm(id)
        return film.toDomainModel(pageNumber = 0)
    }

    override suspend fun updateFilm(film: Film) {
        filmDao.addFilm(film.toEntity(pageNumber = film.page))
    }

    override fun getFavoriteFilmsFlow(): Flow<List<Film>> {
        return filmDao.getAllLikedFilmsFlow()
            .map { entities -> entities.map { entity -> entity.toDomainModel(pageNumber = entity.page) } }
    }
}