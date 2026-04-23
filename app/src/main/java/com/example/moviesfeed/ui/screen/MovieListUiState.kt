package com.example.moviesfeed.ui.screen

import com.example.moviesfeed.data.model.Movie

data class MovieListUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val page: Int = 1
)
