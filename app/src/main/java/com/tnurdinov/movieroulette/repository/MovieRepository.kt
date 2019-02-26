package com.tnurdinov.movieroulette.repository

import com.tnurdinov.movieroulette.MovieResult
import com.tnurdinov.movieroulette.TheMovieDBService

class MovieRepository {

    private val movieService by lazy {
        TheMovieDBService.create()
    }

    suspend fun getRandomMovie(): MovieResult {
        return try {
            val ratedResponse = movieService.discoverMovies().await()
            val random = ratedResponse.results?.random()
            val movieDetails = movieService.getMovieDetails(random?.id ?: 24420).await()
            MovieResult.Success(movieDetails)
        } catch (exception: Exception) {
            MovieResult.Error(exception)
        }
    }

    suspend fun getLast(lastMovieId: Long): MovieResult {
        return try {
            val movieDetails = movieService.getMovieDetails(lastMovieId).await()
            MovieResult.Success(movieDetails)
        } catch (exception: Exception) {
            MovieResult.Error(exception)
        }
    }

}