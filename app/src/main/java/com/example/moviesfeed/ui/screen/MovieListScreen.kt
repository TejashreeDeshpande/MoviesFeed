package com.example.moviesfeed.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesfeed.data.model.Movie
import com.example.moviesfeed.ui.viewmodel.MoviesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen() {

    val viewModel: MoviesViewModel = koinViewModel()

    val movies = viewModel.moviesFlow.collectAsLazyPagingItems()
    val likedIds by viewModel.likedIds.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(
                    count = movies.itemCount,
                    key = { index -> movies[index]?.imdbId ?: index }
                ) { index ->


                    movies[index]?.let { movie ->
                        val isLiked = likedIds.contains(movie.imdbId)

                        MovieItem(
                            movie = movie,
                            isLiked = isLiked,
                            onLikeClick = {
                                viewModel.toggleLike(movie.imdbId, isLiked)
                            }
                        )
                    }
                }

                // Loading footer
                item {
                    when (movies.loadState.append) {
                        is androidx.paging.LoadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        is androidx.paging.LoadState.Error -> {
                            Text("Error loading more")
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    isLiked: Boolean,
    onLikeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(movie.title)

            Spacer(modifier = Modifier.height(8.dp))

            Text("Year: ${movie.year}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("IMDB: ${movie.imdbId}")

                TextButton(onClick = onLikeClick) {
                    Text(
                        text = if (isLiked) "❤️ Liked" else "🤍 Like"
                    )
                }
            }
        }
    }
}