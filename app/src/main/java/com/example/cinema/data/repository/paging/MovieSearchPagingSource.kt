package com.example.cinema.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinema.data.local.entities.FilmEntity
import com.example.cinema.data.remote.films.FilmApi
import com.example.cinema.data.remote.films.dto.FilmModel

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

class MovieSearchPagingSource(
    private val api: FilmApi,
    private val search: String,
    private val apiKey: String
) : PagingSource<Int, FilmEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmEntity> {
        val page = params.key ?: 1
        if (search.isEmpty()) return LoadResult.Page(emptyList(), null, null)
        return try {
            val response = api.searchMovies(search = search, page = page, apikey = apiKey)
            val films = response.results.map {
                it.toEntity(
                    pageNumber = page,
                    userRating = null
                )
            }

            LoadResult.Page(
                data = films,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (films.isEmpty() || page >= response.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FilmEntity>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
