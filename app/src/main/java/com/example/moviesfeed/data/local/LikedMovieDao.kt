package com.example.moviesfeed.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LikedMovieDao {

    @Query("SELECT * FROM liked_movies")
    fun getAllLikedMovies(): Flow<List<LikedMovieEntity>>

    @Query("SELECT imdbId FROM liked_movies WHERE isLiked = 1")
    fun getLikedIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: LikedMovieEntity)

    @Query("DELETE FROM liked_movies WHERE imdbId = :id")
    suspend fun delete(id: String)
}