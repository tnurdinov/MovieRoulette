package com.tnurdinov.movieroulette.repository

import com.tnurdinov.movieroulette.MovieDetails
import com.tnurdinov.movieroulette.ThemoviedbService

class MovieRepository {
    suspend fun getRandomMovie(): MovieDetails {
        return ThemoviedbService.create().getMovieDetails(3).await()
    }
}