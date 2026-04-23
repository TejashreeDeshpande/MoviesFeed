package com.example.moviesfeed.di

import android.util.Log
import androidx.room.Room
import com.example.moviesfeed.data.api.MoviesApi
import com.example.moviesfeed.data.local.AppDatabase
import com.example.moviesfeed.data.local.LikedMovieDao
import com.example.moviesfeed.data.repository.MoviesRepository
import com.example.moviesfeed.ui.viewmodel.MoviesViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json { ignoreUnknownKeys = true }

val networkModule = module {

    single {
        HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl("https://jsonmock.hackerrank.com/")
            .client(get())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    single<MoviesApi> {
        get<Retrofit>().create(MoviesApi::class.java)
    }
}

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "movies_db"
        ).build()
    }

    single<LikedMovieDao> {
        get<AppDatabase>().likedMovieDao()
    }
}

val repositoryModule = module {

    single {
        MoviesRepository(
            api = get(),
            dao = get()
        )
    }
}

val viewModelModule = module {

    viewModel {
        MoviesViewModel(get())
    }
}