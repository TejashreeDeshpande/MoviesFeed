package com.example.moviesfeed.data.api

import com.example.moviesfeed.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("/api/movies")
    suspend fun fetchMovies(
        @Query("page") page: Int,
    ): MoviesResponse
}