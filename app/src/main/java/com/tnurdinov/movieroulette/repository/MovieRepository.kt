package com.tnurdinov.movieroulette.repository

import com.tnurdinov.movieroulette.MovieResult
import com.tnurdinov.movieroulette.TheMovieDBService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MovieRepository {

    private val movieService by lazy {
        TheMovieDBService.create()
    }

    suspend fun getRandomMovie(): MovieResult {
        return try {
            val result = withContext(Dispatchers.IO) {
                val randomPageNum = Random.nextInt(0, 320)
                val ratedResponse = movieService.discoverMovies().await()
                val random = ratedResponse.results?.random()
                movieService.getMovieDetails(random?.id ?: 24420).await()
            }
            MovieResult.Success(result)
        } catch (exception: Exception) {
            MovieResult.Error(exception)
        }
    }

    suspend fun getLast(lastMovieId: Long): MovieResult {
        return try {
            val result = withContext(Dispatchers.IO) {
                movieService.getMovieDetails(lastMovieId).await()
            }
            MovieResult.Success(result)
        } catch (exception: Exception) {
            MovieResult.Error(exception)
        }
    }

}