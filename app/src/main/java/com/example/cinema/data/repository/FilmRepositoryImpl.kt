package com.example.cinema.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.db.FilmDatabase
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.FilmApi
import com.example.cinema.data.remote.dto.FilmModel
import com.example.cinema.data.repository.paging.FilmRemoteMediator
import com.example.cinema.di.ApiKey
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun FilmModel.toEntity(pageNumber: Int): FilmEntity {
    return FilmEntity(
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

class FilmRepositoryImpl @Inject constructor(
    private val filmApi: FilmApi,
    private val filmDao: FilmDao,
    private val db: FilmDatabase,
    @param:ApiKey private val apiKey: String
) :
    FilmRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<FilmEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = false, initialLoadSize = 20,
            ),
            remoteMediator = FilmRemoteMediator(filmApi, db, apiKey),
            pagingSourceFactory = { db.filmDao().getPagingSource() }
        ).flow
    }

    override suspend fun deleteCharacter(id: Int) {
        filmDao.deleteCharacter(id)
    }

    override suspend fun addFilm(film: FilmEntity) {
        filmDao.addFilm(film)
    }

    override suspend fun getFilmById(id: Int): Result<FilmEntity> = withContext(
        Dispatchers.IO
    ) {
        try {
            val localFilm = filmDao.getFilmById(id)
            if (localFilm != null) {
                Result.success(localFilm)
            } else {
                val remoteFilm = filmApi.getFilm(id)
                filmDao.addFilm(remoteFilm.toEntity(pageNumber = 0))
                Result.success(remoteFilm.toEntity(pageNumber = 0))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFilmLike(id: Int){
        val likeInfo = filmDao.getFilmById(id)?.isFavorite
        val likeChangedFilm = likeInfo?.let {filmDao.getFilmById(id)?.copy(isFavorite = !likeInfo)}
        likeChangedFilm?.let { filmDao.addFilm(likeChangedFilm) }
    }
}