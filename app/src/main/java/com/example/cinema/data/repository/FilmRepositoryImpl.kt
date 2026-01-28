package com.example.cinema.data.repository

import android.R.attr.apiKey
import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.local.db.CinemaDatabase
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.local.entities.LikedFilmsEntity
import com.example.cinema.data.local.entities.RatedFilmsEntity
import com.example.cinema.data.remote.films.FilmApi
import com.example.cinema.data.remote.films.dto.FilmModel
import com.example.cinema.data.repository.paging.FilmRemoteMediator
import com.example.cinema.data.repository.paging.FilmSearchRemoteMediator
import com.example.cinema.di.ApiKey
import com.example.cinema.domain.model.Film
import com.example.cinema.domain.repository.FilmRepository
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.Int

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
        photos = this.photos,
        userRating = this.userRating
    )
}

private fun FilmEntity.toLikeEntity(): LikedFilmsEntity {
    return LikedFilmsEntity(
        id = this.id,
        isFavorite = this.isFavorite,
        image = this.image,
        releaseDate = this.releaseDate,
        adult = this.adult,
        overview = this.overview,
        page = this.page,
        rating = this.rating,
        popularity = this.popularity,
        language = this.language,
        runtime = this.runtime,
        video = this.video,
        photos = this.photos,
        userRating = this.userRating,
        title = this.title
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
        photos = photos,
        userRating = null
    )
}

private fun LikedFilmsEntity.toDomainModel(): Film {
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
        photos = this.photos,
        userRating = this.userRating
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
        photos = this.photos,
        userRating = this.userRating
    )
}

enum class SortType {
    POPULARITY, USER_RATE
}

class FilmRepositoryImpl @Inject constructor(
    private val filmApi: FilmApi,
    private val filmDao: FilmDao,
    private val db: CinemaDatabase,
    @param:ApiKey private val apiKey: String,
    @ApplicationContext private val context: Context
) :
    FilmRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getFilms(sortType: SortType, search: String): Flow<PagingData<Film>> {
        return if (search.isEmpty()) {
            if (sortType == SortType.USER_RATE) {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = true
                    ),
                    pagingSourceFactory = {
                        db.filmDao().sortPagingByUserRating()
                    }).flow.map { pagingData ->
                    pagingData.map { entity ->
                        entity.toDomainModel()
                    }
                }

            } else {
                Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = true,
                        initialLoadSize = 20
                    ),
                    remoteMediator = FilmRemoteMediator(filmApi, db, apiKey, context),
                    pagingSourceFactory =
                        {
                            db.filmDao().getPagingSource()
                        }).flow.map { pagingData ->
                    pagingData.map { entity ->
                        entity.toDomainModel()
                    }
                }
            }
        } else {
            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true,
                    initialLoadSize = 20
                ),
                remoteMediator = FilmSearchRemoteMediator(
                    filmApi,
                    db,
                    apiKey = apiKey,
                    search = search
                ),
                pagingSourceFactory = {
                    db.filmDao().searchFilmsPagingSource(search)
                }
            ).flow.map { pagingData ->
                pagingData.map { entity ->
                    entity.toDomainModel()
                }
            }
        }

    }

    override suspend fun getFilmByIdFromLocal(id: Int): Film? {
        val film = filmDao.getFilmById(id)
        return film?.toDomainModel()
    }

    override suspend fun getFilmByIdFromRemote(id: Int): Film {
        val film = filmApi.getFilm(id, apiKey)
        val video = filmApi.getFilmVideos(id, apiKey)
        val localFilm = filmDao.getFilmById(id)
        val likeInfo = filmDao.getFilmLikeInfo(id)
        val photos: List<String> =
            filmApi.getFilmImages(id, apiKey).backdrops?.mapNotNull { it -> it.photo }
                ?: emptyList()
        val gettedFilm = film.toDomainModel(
            isFavorite = likeInfo,
            pageNumber = localFilm?.page ?: 0,
            video = video.results?.firstOrNull { it.type == "Trailer" }?.videoKey
                ?: video.results?.firstOrNull()?.videoKey,
            photos = photos,
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
        Log.e("FILMdbINSERTRESULT", "$result")
        return gettedFilm

    }

    override suspend fun updateFilm(film: Film) {
        filmDao.addFilm(film.toEntity())
    }

    override suspend fun toggleFilmLike(likeStatus: Boolean, id: Int?) {
        filmDao.toggleFilmLike(likeStatus, id)
        if (likeStatus) {
            filmDao.getFilmById(id)?.toLikeEntity()?.let {
                filmDao.addLikedFilm(it)
            }
        } else {
            filmDao.deleteLikedFilm(id)
        }
        Log.e("newlikestatus in repo", "$likeStatus")
    }

    override fun getFilmFlow(id: Int): Flow<Film?> {
        return filmDao.getFilmFlow(id)
            .combine(filmDao.getLikeInfoFlow(id)) { entity, isLiked ->
                entity?.toDomainModel()?.copy(isFavorite = isLiked)
            }

    }

    override fun getFavoriteFilmsFlow(): Flow<List<Film>> {
        return filmDao.getAllLikedFilmsFlow()
            .map { entities ->
                entities.map { entity ->
                    entity.toDomainModel()
                }
            }
    }

    override suspend fun updateFilmRating(id: Int, newRating: Int) {
        filmDao.updateFilmRatingEntities(RatedFilmsEntity(id = id, userRating = newRating))
        filmDao.updateFilmRating(newRating, id)
        println(filmDao.getFilmById(id)?.userRating)
    }

    override suspend fun getLikeInfoById(id: Int): Boolean {
       return filmDao.getFilmLikeInfo(id)
    }

    override fun getAllUserRatingsSum(): Flow<Int> {
        return filmDao.getAllUserRatings()
    }

    override fun getLikedFilmsAmount(): Flow<Int> {
        return filmDao.getLikedFilmsAmount()
    }

    override fun getRatedFilmsAmount(): Flow<Int> {
        return filmDao.getRatedFilmsAmount()
    }

    override suspend fun manualRefresh() {
        filmDao.invalidateFilms()
    }
}
