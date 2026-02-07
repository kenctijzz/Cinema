package com.example.cinema.data.repository

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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.Int

private fun FilmEntity.toDomainModel(
): Film {
    return Film(
        id = this.id,
        title = this.title ?: "Без названия",
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
        userRating = this.userRating,
        posters = this.posters,
        similarFilms = this.similarFilms.map { it.toDomainModel() }
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
        title = this.title ?: "Без названия",
        posters = this.posters,
        similarFilms = this.similarFilms
    )

}

private fun FilmModel.toDomainModel(
    pageNumber: Int?,
    video: String?,
    photos: List<String>,
    posters: List<String>,
    similarFilms: List<Film>,
    isFavorite: Boolean
): Film {
    return Film(
        id = this.kinopoiskId,
        title = this.nameRu,
        image = this.posterUrl,
        releaseDate = this.releaseDate,
        adult = this.adult,
        overview = this.description,
        isFavorite = false,
        page = pageNumber,
        rating = this.rating,
        popularity = this.popularity,
        language = this.language,
        runtime = this.runtime,
        video = video,
        photos = photos,
        posters = posters,
        userRating = null,
        similarFilms = similarFilms
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
        userRating = this.userRating,
        posters = this.posters,
        similarFilms = this.similarFilms.map { it.toDomainModel() }
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
        userRating = this.userRating,
        posters = this.posters,
        similarFilms = this.similarFilms.map { it.toEntity() }
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

    override suspend fun getFilmByIdFromLocal(id: Int?): Film? {
        val film = filmDao.getFilmById(id)
        return film?.toDomainModel()
    }

    override suspend fun getFilmByIdFromRemote(id: Int?): Film {
        val film = filmApi.getFilm(id, apiKey)
        val video = filmApi.getFilmVideos(id, apiKey)
        val localFilm = filmDao.getFilmById(id)
        val likeInfo = filmDao.getFilmLikeInfo(id)
        val localFavorites = db.filmDao().getAllLikedFilms()
        val photos: List<String> =
            filmApi.getFilmImages(id = id, apikey = apiKey, type = "STILL").backdrops?.mapNotNull { it -> it.photo }
                ?: emptyList()
        val posters: List<String> =
            filmApi.getFilmImages(id = id, apikey = apiKey, type = "POSTER").backdrops?.mapNotNull { it -> it.photo }
                ?: emptyList()
        val similarFilms: List<Film> =
            filmApi.getFilmSimilars(id = id, apikey = apiKey).results.map { it.toDomainModel(
                pageNumber = 0,
                video = "",
                photos = emptyList(),
                posters = emptyList(),
                similarFilms = emptyList(),
                isFavorite = localFavorites.contains(it.kinopoiskId)
            ) }
        val gettedFilm = film.toDomainModel(
            pageNumber = localFilm?.page,
            video = video.results?.firstOrNull { it.type == "Trailer" }?.videoKey
                ?: video.results?.firstOrNull()?.videoKey,
            photos = photos,
            posters = posters,
            similarFilms = similarFilms,
            isFavorite = likeInfo
        )
        val entity = gettedFilm.toEntity()
        val result = filmDao.insertInitialFilm(entity)
        if (result == -1L) {
            filmDao.updateFilmDetails(
                id = gettedFilm.id,
                runtime = gettedFilm.runtime,
                video = gettedFilm.video,
                photos = gettedFilm.photos,
                posters = gettedFilm.posters,
                similarFilms = gettedFilm.similarFilms.map { it.toEntity() }
            )
        }
        Log.e("FILMdbINSERTRESULT", "$result")
        return gettedFilm

    }

    override suspend fun updateFilm(film: Film) {
        filmDao.addFilm(film.toEntity())
    }

    override suspend fun toggleFilmLike(likeStatus: Boolean, film: Film) {
        val localFilm = filmDao.getFilmById(film.id)

        if (localFilm == null) {
            val newEntity = film.toEntity().copy(
                isFavorite = likeStatus,
                page = 0
            )
            filmDao.insertInitialFilm(newEntity)
            Log.e("ToggleLikeInfo","$localFilm")
        } else {
            filmDao.toggleFilmLike(id = film.id, likeStatus = likeStatus)
            Log.e("ToggleLikeInfo", "$localFilm")
        }

        if (likeStatus) {
            filmDao.getFilmById(film.id)?.toLikeEntity()?.let {
                filmDao.addLikedFilm(it)
            }
        } else {
            filmDao.deleteLikedFilm(film.id)
        }
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

    override suspend fun deleteFilmUserRating(id: Int) {
        filmDao.deleteFilmUserRating(id)
        filmDao.updateFilmRating(id = id, newRating = null)
    }
}
