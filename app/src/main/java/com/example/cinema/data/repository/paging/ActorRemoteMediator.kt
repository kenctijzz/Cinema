package com.example.cinema.data.repository.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.InitializeAction
import androidx.paging.RemoteMediator.MediatorResult
import androidx.room.withTransaction
import com.example.cinema.data.local.db.CinemaDatabase
import com.example.cinema.data.local.entities.ActorEntity
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.actors.ActorApi
import com.example.cinema.data.remote.actors.dto.ActorModel
import com.example.cinema.data.remote.films.FilmApi
import kotlinx.coroutines.delay

private fun ActorModel.toEntity(pageNumber: Int): ActorEntity {
    return ActorEntity(
        id = this.id,
        gender = this.gender,
        popularity = this.popularity,
        birthday = null,
        deathday = null,
        biography = null,
        name = this.name,
        image = this.image,
        isFavorite = false,
        page = pageNumber
    )
}

@OptIn(ExperimentalPagingApi::class)
class ActorRemoteMediator(
    private val api: ActorApi,
    private val db: CinemaDatabase,
    private val apiKey: String
) : RemoteMediator<Int, ActorEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ActorEntity>
    ): MediatorResult {

        return try {

            val page = when (loadType)
            {

                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    Log.d("paging", "Loading page")
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else lastItem.page + 1
                }
            }

            val response = api.getPopularActors(page = page, apikey = apiKey)
            val localFavorites = db.actorDao().getAllLikedActors()
            val actors = response.results.map { actorModel ->
                actorModel.toEntity(pageNumber = page)
                    .copy(isFavorite = localFavorites.contains(actorModel.id))
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.actorDao().clearAll()
                }
                db.actorDao().insertAll(actors)
            }
            MediatorResult.Success(endOfPaginationReached = actors.isEmpty())
        } catch (e: Exception) {

            if (loadType == LoadType.REFRESH) {

                val hasData = db.actorDao().getAnyActor() != null
                if (hasData) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }

            }
            MediatorResult.Error(e)
        }
    }
}