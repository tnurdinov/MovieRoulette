package com.tnurdinov.movieroulette.repository

import com.tnurdinov.movieroulette.model.MovieDetails
import com.tnurdinov.movieroulette.TheMovieDBService
import kotlin.random.Random

class MovieRepository {

    private val movieService by lazy {
        TheMovieDBService.create()
    }

    suspend fun getRandomMovie(): MovieDetails {
        val randomPageNum = Random.nextInt(0, 320)
        val ratedResponse = movieService.getTopRatedMovies(page = randomPageNum).await()
        val random = ratedResponse.results?.random()
        return movieService.getMovieDetails(random?.id ?: 24420).await()
    }
}