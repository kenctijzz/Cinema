package com.example.cinema.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.FilmApi
import com.example.cinema.data.remote.dto.FilmModel
import com.example.cinema.data.repository.paging.FilmPagingSource
import com.example.cinema.di.ApiKey
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun FilmModel.toEntity(): FilmEntity {
    return FilmEntity(
        id = this.id,
        title = this.title,
        image = this.image,
        releaseDate = this.releaseDate,
        adult = this.adult,
        overview = this.overview,
        page = 0
    )
}

class FilmRepositoryImpl @Inject constructor(
    private val filmApi: FilmApi,
    private val filmDao: FilmDao,
    @param:ApiKey private val apiKey: String
) :
    FilmRepository {
    override fun getPopularMovies(): Flow<PagingData<FilmModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 40
            ),
            pagingSourceFactory = {
                FilmPagingSource(
                    api = filmApi,
                    apiKey = apiKey,
                    dao = filmDao
                )
            }
        ).flow
    }

    override suspend fun deleteCharacter(id: Int) {
        filmDao.deleteCharacter(id)
    }

    override suspend fun addFilm(film: FilmEntity) {
        filmDao.addFilm(film)
    }

    override suspend fun getMovieById(id: Int): Result<FilmEntity> = withContext(
        Dispatchers.IO
    ) {
        try {
            val localMovie = filmDao.getFilmById(id)
            if (localMovie != null) {
                Result.success(localMovie)
            } else {
                val remoteMovie = filmApi.getMovie(id)
                filmDao.addFilm(remoteMovie.toEntity())
                Result.success(remoteMovie.toEntity())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}