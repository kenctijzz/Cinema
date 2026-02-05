package com.example.cinema.data.repository.paging

import android.R.attr.apiKey
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.InitializeAction
import androidx.paging.RemoteMediator.MediatorResult
import androidx.room.withTransaction
import com.example.cinema.data.local.db.CinemaDatabase
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.films.FilmApi
import com.example.cinema.data.remote.films.dto.FilmModel

private fun FilmModel.toEntity(
    pageNumber: Int,
    videos: List<String> = emptyList(),
    photos: List<String> = emptyList(),
    userRating: Int?,
    isSearchResult: Boolean
): FilmEntity {
    return FilmEntity(
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
        video = null,
        photos = photos,
        userRating = userRating,
        isSearchResult = isSearchResult
    )
}

@OptIn(ExperimentalPagingApi::class)
class FilmSearchRemoteMediator(
    private val api: FilmApi,
    private val db: CinemaDatabase,
    private val search: String,
    private val apiKey: String
) : RemoteMediator<Int, FilmEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
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

            val response = api.searchMovies(search = search, page = page, apikey = apiKey)
            val localFavorites = db.filmDao().getAllLikedFilms()
            Log.e("Liked Films", "$localFavorites")
            val localRated =
                db.filmDao().getAllRatedFilmsId().associate { it.id to it.userRating }
            val localFilms = db.filmDao().getAllFilms()
            val films = response.results.map { filmModel ->
                Log.d("CHECK_ID", "Фильм: ${filmModel.nameRu}, ID: ${filmModel.kinopoiskId}")
                filmModel.toEntity(
                    pageNumber = page,
                    userRating = localRated[filmModel.kinopoiskId],
                    isSearchResult = true
                )
                    .copy(
                        isFavorite = localFavorites.contains(filmModel.kinopoiskId)
                    )
            }
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.filmDao().clearSearchFilms()
                }
                db.filmDao().insertAll(films)
            }

            MediatorResult.Success(endOfPaginationReached = films.isEmpty())
        } catch (e: Exception) {
            Log.e("SEARCH ERROR", "$e")
            if (loadType == LoadType.REFRESH) {


                val hasData = db.filmDao().getAnyFilm()?.isSearchResult
                if (hasData == true) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }

            }
            MediatorResult.Error(e)
        }
    }
}
