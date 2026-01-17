package com.example.cinema.data.repository.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.Dao
import com.example.cinema.data.local.dao.FilmDao
import com.example.cinema.data.remote.FilmApi
import com.example.cinema.data.remote.dto.FilmModel
import com.example.cinema.data.repository.toEntity
import com.example.cinema.di.ApiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmPagingSource(
    private val api: FilmApi,
    private val apiKey: String,
    private val dao: FilmDao
) : PagingSource<Int, FilmModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmModel> {
        val position = params.key ?: 1
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPopularMovies(page = position, apikey = apiKey)
                dao.insertAll(response.results.map { it.toEntity() })
                val films = response.results
                LoadResult.Page(
                    data = films.distinctBy { it.id },
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (films.isEmpty()) null else position + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FilmModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}