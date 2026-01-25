package com.example.cinema.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.db.CinemaDatabase
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.films.FilmApi
import com.example.cinema.data.remote.films.dto.FilmModel
import com.example.cinema.data.repository.paging.FilmRemoteMediator
import com.example.cinema.di.ApiKey
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private fun FilmEntity.toDomainModel(): Film {
    return Film(
        id = this.id,
        title = this.title,
        image = this.image,
        releaseDate = this.releaseDate,
        adult = this.adult,
        overview = this.overview,
        isFavorite = this.isFavorite,
        page = this.page,
        rating = this.rating,
        popularity = this.popularity,
        language = this.language,
        runtime = this.runtime,
        video = this.video,
        photos = this.photos
    )
}

private fun FilmModel.toDomainModel(
    pageNumber: Int,
    video: String?,
    photos: List<String>,
    isFavorite: Boolean
): Film {
    return Film(
        id = this.id,
        title = this.title,
        image = this.image,
        releaseDate = this.releaseDate,
        adult = this.adult,
        overview = this.overview,
        isFavorite = false,
        page = pageNumber,
        rating = this.rating,
        popularity = this.popularity,
        language = this.language,
        runtime = this.runtime,
        video = video,
        photos = photos
    )
}

fun Film.toEntity(): FilmEntity {
    return FilmEntity(
        id = this.id,
        title = this.title,
        image = this.image,
        releaseDate = this.releaseDate,
        adult = this.adult,
        overview = this.overview,
        isFavorite = this.isFavorite,
        page = this.page,
        rating = this.rating,
        popularity = this.popularity,
        language = this.language,
        runtime = this.runtime,
        video = this.video,
        photos = this.photos
    )
}

class FilmRepositoryImpl @Inject constructor(
    private val filmApi: FilmApi,
    private val filmDao: FilmDao,
    private val db: CinemaDatabase,
    @param:ApiKey private val apiKey: String
) :
    FilmRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularMovies(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = true, initialLoadSize = 20,
            ),
            remoteMediator = FilmRemoteMediator(filmApi, db, apiKey),
            pagingSourceFactory = { db.filmDao().getPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toDomainModel()
            }
        }
    }


    override suspend fun getFilmByIdFromLocal(id: Int): Film {
        val film = filmDao.getFilmById(id) ?: throw Exception("Фильм не найден")
        return film.toDomainModel()
    }

    override suspend fun getFilmByIdFromRemote(id: Int): Film {
        val film = filmApi.getFilm(id, apiKey)
        val video = filmApi.getFilmVideos(id, apiKey)
        val localFilm = filmDao.getFilmById(id)
        val photos: List<String> =
            filmApi.getFilmImages(id, apiKey).backdrops?.mapNotNull { it -> it.photo }
                ?: emptyList()
        val gettedFilm = film.toDomainModel(
            isFavorite = localFilm?.isFavorite ?: false,
            pageNumber = localFilm?.page ?: 0,
            video = video.results?.firstOrNull { it.type == "Trailer" }?.videoKey
                ?: video.results?.firstOrNull()?.videoKey,
            photos = photos
        )
        val entity = gettedFilm.toEntity()
        val result = filmDao.insertInitialFilm(entity)
        if (result == -1L) {
            filmDao.updateFilmDetails(
                id = gettedFilm.id,
                runtime = gettedFilm.runtime,
                video = gettedFilm.video,
                photos = gettedFilm.photos
            )
        }
        return gettedFilm

    }

    override suspend fun updateFilm(film: Film) {
        filmDao.addFilm(film.toEntity())
    }

    override suspend fun toggleFilmLike(likeStatus: Boolean, id: Int) {
        filmDao.toggleFilmLike(likeStatus,id)
    }
    override fun getFilmFlow(id: Int): Flow<Film> {
        return filmDao.getFilmFlow(id).map { entity -> entity.toDomainModel() }
    }

    override fun getFavoriteFilmsFlow(): Flow<List<Film>> {
        return filmDao.getAllLikedFilmsFlow()
            .map { entities ->
                entities.map { entity ->
                    entity.toDomainModel()
                }
            }
    }
}