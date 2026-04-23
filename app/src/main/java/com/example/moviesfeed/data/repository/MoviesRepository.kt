package com.example.moviesfeed.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviesfeed.data.api.MoviesApi
import com.example.moviesfeed.data.local.LikedMovieDao
import com.example.moviesfeed.data.local.LikedMovieEntity
import com.example.moviesfeed.data.model.Movie
import com.example.moviesfeed.ui.screen.MoviesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepository(
    private val api: MoviesApi,
    private val dao: LikedMovieDao
) {
    init {
        Log.d("Repo", "created")
    }
    fun getMovies(): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviesPagingSource(api)
            }
        )
    }

    fun getLikedIds(): Flow<Set<String>> {
        return dao.getLikedIds().map { it.toSet() }
    }

    suspend fun toggleLike(movieId: String, isLiked: Boolean) {
        if (isLiked) {
            dao.insert(LikedMovieEntity(imdbId = movieId))
        } else {
            dao.delete(movieId)
        }
    }
}
