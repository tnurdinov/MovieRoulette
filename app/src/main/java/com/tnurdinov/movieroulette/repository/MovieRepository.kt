package com.tnurdinov.movieroulette.repository

import com.tnurdinov.movieroulette.MovieDetails
import com.tnurdinov.movieroulette.TheMovieDBService

class MovieRepository {
    suspend fun getRandomMovie(): MovieDetails {
        val ratedResponse = TheMovieDBService.create().getTopRatedMovies().await()
        val random = ratedResponse.results?.random()
        return TheMovieDBService.create().getMovieDetails(random?.id ?: 24420).await()
    }
}