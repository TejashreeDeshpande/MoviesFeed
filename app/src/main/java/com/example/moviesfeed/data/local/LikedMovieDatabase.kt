package com.example.moviesfeed.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LikedMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedMovieDao(): LikedMovieDao
}