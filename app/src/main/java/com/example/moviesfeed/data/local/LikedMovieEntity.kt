package com.example.moviesfeed.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_movies")
data class LikedMovieEntity(
    @PrimaryKey val imdbId: String,
    val isLiked: Boolean = true
)