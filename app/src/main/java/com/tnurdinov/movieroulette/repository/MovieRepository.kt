package com.tnurdinov.movieroulette.repository

import com.tnurdinov.movieroulette.MovieDetails
import com.tnurdinov.movieroulette.TheMovieDBService
import kotlin.random.Random

class MovieRepository {
    suspend fun getRandomMovie(): MovieDetails {
        val randomPageNum = Random.nextInt(0, 320)
        val ratedResponse = TheMovieDBService.create().getTopRatedMovies(page = randomPageNum).await()
        val random = ratedResponse.results?.random()
        return TheMovieDBService.create().getMovieDetails(random?.id ?: 24420).await()
    }
}