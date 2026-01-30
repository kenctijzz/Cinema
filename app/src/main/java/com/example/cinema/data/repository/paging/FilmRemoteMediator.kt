package com.example.cinema.data.repository.paging

import android.R.attr.apiKey
import android.content.Context
import android.util.Log
import android.util.Log.e
import android.widget.Toast
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
import com.example.cinema.data.repository.toEntity
import com.example.cinema.ui.common.mockMovies
import com.example.cinema.ui.utils.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

private fun FilmModel.toEntity(
    pageNumber: Int,
    videos: List<String> = emptyList(),
    photos: List<String> = emptyList(),
    userRating: Int?,
    isSearchResult: Boolean
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
        userRating = userRating,
        isSearchResult = isSearchResult
    )
}


@OptIn(ExperimentalPagingApi::class)
class FilmRemoteMediator (
    private val api: FilmApi,
    private val db: CinemaDatabase,
    private val apiKey: String,
    private val context: Context
) : RemoteMediator<Int, FilmEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmEntity>
    ): MediatorResult {
        Log.d("Mediator", "Pull-to-refresh triggered!")
        if (!isNetworkAvailable(context)) {
            if(loadType == LoadType.REFRESH && db.filmDao().getAnyFilm() == null) {
                val entities = mockMovies.map { film ->
                    FilmEntity(
                        id = film.id,
                        title = film.title,
                        image = film.image,
                        releaseDate = film.releaseDate,
                        adult = film.adult,
                        overview = film.overview,
                        isFavorite = false,
                        page = 1,
                        rating = film.rating,
                        popularity = film.popularity,
                        language = film.language,
                        runtime = film.runtime,
                        video = film.video,
                        photos = film.photos,
                        userRating = null,
                        isSearchResult = false)
                }

                db.withTransaction {
                    db.filmDao().insertAll(entities)
                }
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            return if (loadType == LoadType.REFRESH && db.filmDao().getAnyFilm() != null) {
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Error(java.io.IOException("Отсутствует интернет соединение"))
            }
        }
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
            Log.e("Liked Films", "$localFavorites")
            val localRated = db.filmDao().getAllRatedFilmsId().associate { it.id to it.userRating }
            println(localRated)
            val localFilms = db.filmDao().getAllFilms()
            val films = response.results.map { filmModel ->
                filmModel.toEntity(
                    pageNumber = page,
                    userRating = localRated[filmModel.id],
                    isSearchResult = false
                )
                    .copy(
                        isFavorite = localFavorites.contains(filmModel.id)
                    )
            }
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.filmDao().clearPopularFilms()
                    val mockIds = listOf(101,103,104,105)
                    mockIds.forEach { id ->
                        db.filmDao().deleteFilmUserRating(id)
                        db.filmDao().deleteLikedFilm(id)
                        db.filmDao().deleteFilm(id)
                    }
                }
                db.filmDao().insertAll(films)
            }

            MediatorResult.Success(endOfPaginationReached = films.isEmpty())
        } catch (e: Exception) {
            if(db.filmDao().getAnyFilm() == null){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Загружен офлайн-каталог", Toast.LENGTH_LONG).show()
                }
                val entities = mockMovies.map { film ->
                    FilmEntity(
                        id = film.id,
                        title = film.title,
                        image = film.image,
                        releaseDate = film.releaseDate,
                        adult = film.adult,
                        overview = film.overview,
                        isFavorite = false,
                        page = 1,
                        rating = film.rating,
                        popularity = film.popularity,
                        language = film.language,
                        runtime = film.runtime,
                        video = film.video,
                        photos = film.photos,
                        userRating = null,
                        isSearchResult = false
                    )
                }
                db.withTransaction {
                    db.filmDao().insertAll(entities)
                }
                delay(500)
                Log.d("Mediator", "Mocks inserted, count: ${db.filmDao().getAllFilms().size}")
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            MediatorResult.Error(e)
            }

        }
    }
