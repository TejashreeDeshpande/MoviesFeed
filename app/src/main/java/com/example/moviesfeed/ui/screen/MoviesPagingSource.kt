package com.example.moviesfeed.ui.screen

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesfeed.data.api.MoviesApi
import com.example.moviesfeed.data.model.Movie

class MoviesPagingSource(
    private val api: MoviesApi
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {

            val page = params.key ?: 1
            val response = api.fetchMovies(page)

            LoadResult.Page(
                data = response.movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}