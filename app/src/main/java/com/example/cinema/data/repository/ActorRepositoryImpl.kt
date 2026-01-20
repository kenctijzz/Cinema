package com.example.cinema.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cinema.data.local.dao.ActorDao
import com.example.cinema.data.local.db.CinemaDatabase
import com.example.cinema.data.local.entities.ActorEntity
import com.example.cinema.data.remote.actors.ActorApi
import com.example.cinema.data.remote.actors.dto.ActorModel
import com.example.cinema.data.repository.paging.ActorRemoteMediator
import com.example.cinema.di.ApiKey
import com.example.cinema.domain.model.Actor
import com.example.cinema.domain.repository.ActorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

private fun ActorEntity.toDomainModel(): Actor {
    return Actor(
        id = this.id,
        gender = this.gender,
        popularity = this.popularity,
        birthday = this.birthday,
        deathday = this.deathday,
        biography = this.biography,
        name = this.name,
        page = this.page,
        image = this.image,
        isFavorite = this.isFavorite
    )
}
private fun ActorModel.toDomainModel(pageNumber: Int): Actor {
    return Actor(
        id = this.id,
        gender = this.gender,
        popularity = this.popularity,
        birthday = null,
        deathday = null,
        biography = null,
        name = this.name,
        page = pageNumber,
        image = this.image,
        isFavorite = false
    )
}
private fun Actor.toEntity(): ActorEntity {
    return ActorEntity(
        id = this.id,
        gender = this.gender,
        popularity = this.popularity,
        birthday = this.birthday,
        deathday = this.deathday,
        biography = this.biography,
        name = this.name,
        page = this.page,
        image = this.image,
        isFavorite = this.isFavorite
    )
}
class ActorRepositoryImpl @Inject constructor(
    private val actorDao: ActorDao,
    private val actorApi: ActorApi,
    private val db: CinemaDatabase,
    @param:ApiKey private val apiKey: String
) : ActorRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPopularActors(): Flow<PagingData<Actor>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = false, initialLoadSize = 20,
            ),
            remoteMediator = ActorRemoteMediator(actorApi, db, apiKey),
            pagingSourceFactory = { db.actorDao().getPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomainModel() }
        }
    }
    override suspend fun getActorByIdFromLocal(id: Int): Actor {
        val actor = actorDao.getActorById(id) ?: throw Exception("Фильм не найден")
        return actor.toDomainModel()
    }

    override suspend fun getActorByIdFromRemote(id: Int): Actor {
        val actor = actorApi.getActor(id)
        return actor.toDomainModel(pageNumber = 0)
    }

    override suspend fun updateActor(actor: Actor) {
        actorDao.addActor(actor.toEntity())
    }

    override fun getFavoriteActorsFlow(): Flow<List<Actor>> {
        return actorDao.getAllLikedActorsFlow()
            .map { entities -> entities.map { entity -> entity.toDomainModel() } }
    }
}