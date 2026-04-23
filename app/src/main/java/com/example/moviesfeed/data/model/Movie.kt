package com.example.moviesfeed.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("Title")
    val title: String,
    @SerialName("Year")
    val year: Int,
    @SerialName("imdbID")
    val imdbId: String
)