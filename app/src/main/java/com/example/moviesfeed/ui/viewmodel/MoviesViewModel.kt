package com.example.moviesfeed.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviesfeed.data.repository.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(val repository: MoviesRepository) : ViewModel() {

    val moviesFlow = repository.getMovies()
        .flow
        .cachedIn(viewModelScope)

    val likedIds = repository.getLikedIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    fun toggleLike(movieId: String, isCurrentlyLiked: Boolean) {
        viewModelScope.launch {
            repository.toggleLike(movieId, !isCurrentlyLiked)
        }
    }

//
//    private val _likedMovies = MutableStateFlow<Set<String>>(emptySet())
//    val likedMovies: StateFlow<Set<String>> = _likedMovies
//
//    fun toggleLike(movieId: String) {
//        _likedMovies.update { current ->
//            if (movieId in current) current - movieId
//            else current + movieId
//        }
//    }

}