package com.example.cinema.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cinema.data.local.db.FilmDatabase
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.FilmApi
import com.example.cinema.data.repository.toEntity
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagingApi::class)
class FilmRemoteMediator(
    private val api: FilmApi,
    private val db: FilmDatabase,
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
            delay(500)
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else lastItem.page + 1
                }
            }

            val response = api.getPopularMovies(page = page, apikey = apiKey)
            val films = response.results.map { it.toEntity(pageNumber = page) }

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