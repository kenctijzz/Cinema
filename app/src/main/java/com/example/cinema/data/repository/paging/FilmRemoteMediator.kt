package com.example.cinema.data.repository.paging

import android.R
import android.R.attr.apiKey
import android.util.Log
import android.util.Log.e
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cinema.data.local.db.CinemaDatabase
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.films.FilmApi
import com.example.cinema.data.remote.films.dto.FilmModel
import com.example.cinema.data.repository.paging.toEntity

import kotlinx.coroutines.delay

private fun FilmModel.toEntity(
    pageNumber: Int,
    videos: List<String> = emptyList(),
    photos: List<String> = emptyList(),
    userRating: Int?
): FilmEntity {
    return FilmEntity(
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
        video = null,
        photos = photos,
        userRating = userRating
    )
}


@OptIn(ExperimentalPagingApi::class)
class FilmRemoteMediator(
    private val api: FilmApi,
    private val db: CinemaDatabase,
    private val apiKey: String
) : RemoteMediator<Int, FilmEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else lastItem.page + 1
                }
            }

            val response = api.getPopularMovies(page = page, apikey = apiKey)
            val localFavorites = db.filmDao().getAllLikedFilms()
            val localRated = db.filmDao().getAllRatedFilmsId().associate { it.id to it.userRating }
            val films = response.results.map { filmModel ->
                filmModel.toEntity(pageNumber = page, userRating = localRated[filmModel.id])
                    .copy(
                        isFavorite = localFavorites.contains(filmModel.id)
                    )
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.filmDao().clearAll()
                }
                db.filmDao().insertAll(films)
            }

            MediatorResult.Success(endOfPaginationReached = films.isEmpty())
        } catch (e: Exception) {
            if (loadType == LoadType.REFRESH) {

                val hasData = db.filmDao().getAnyFilm() != null
                if (hasData) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }

            }
            MediatorResult.Error(e)
        }
    }
}