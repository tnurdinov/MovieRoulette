package com.tnurdinov.movieroulette.repository

import com.tnurdinov.movieroulette.MovieResult
import com.tnurdinov.movieroulette.TheMovieDBService
import retrofit2.HttpException

class MovieRepository {

    private val movieService by lazy {
        TheMovieDBService.create()
    }

    suspend fun getRandomMovie(): MovieResult {
        return try {
            val ratedResponse = movieService.discoverMoviesAsync().await()
            val random = ratedResponse.results?.random()
            val movieDetails = movieService.getMovieDetailsAsync(random?.id ?: 24420).await()
            MovieResult.Success(movieDetails.body()!!)
        } catch (e: HttpException) {
            MovieResult.Error(e)
        } catch (e: Throwable) {
            MovieResult.Error(e)
        }
    }

    suspend fun getLast(lastMovieId: Long): MovieResult {
        return try {
            val movieDetails = movieService.getMovieDetailsAsync(lastMovieId).await()
            MovieResult.Success(movieDetails.body()!!)
        } catch (exception: Exception) {
            MovieResult.Error(exception)
        }
    }

}