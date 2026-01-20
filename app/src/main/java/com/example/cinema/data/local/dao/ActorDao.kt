package com.example.cinema.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema.data.local.entities.ActorEntity
import com.example.cinema.domain.model.Actor
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao {
    @Query("SELECT * FROM actors ORDER BY page ASC, id ASC")
    fun getPagingSource(): PagingSource<Int, ActorEntity>

    @Query("SELECT id FROM actors WHERE isFavorite = 1")
    suspend fun getAllLikedActors(): List<Int>

    @Query("SELECT * FROM actors LIMIT 1")
    suspend fun getAnyActor(): ActorEntity?

    @Query("DELETE FROM actors")
    suspend fun clearAll()

    @Query("UPDATE actors SET isFavorite = :likeStatus WHERE id = :id")
    suspend fun toggleActorLike(likeStatus: Boolean, id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actors: List<ActorEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActor(actor: ActorEntity)

    @Query("SELECT * FROM actors WHERE isFavorite = 1")
    fun getAllLikedActorsFlow(): Flow<List<ActorEntity>>

    @Query("SELECT * FROM actors WHERE id = :id")
    suspend fun getActorById(id: Int): ActorEntity?

}