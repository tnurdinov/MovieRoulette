package com.tnurdinov.movieroulette.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tnurdinov.movieroulette.TheMovieDBService
import com.tnurdinov.movieroulette.model.MovieDetails
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlin.random.Random

class MovieRepository {

    private val randomMovie: MutableLiveData<MovieDetails> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()
    private val noConnection = "No Internet Access"

    private val movieService by lazy {
        TheMovieDBService.create()
    }

    suspend fun getRandomMovie() {
        try {
            val randomPageNum = Random.nextInt(0, 320)
            val ratedResponse = movieService.getTopRatedMovies(page = randomPageNum).await()
            val random = ratedResponse.results?.random()
            val movieDetails = movieService.getMovieDetails(random?.id ?: 24420).await()
            randomMovie.postValue(movieDetails)
        } catch (httpException: HttpException) {
            errorMessage.postValue(httpException.message)
        } catch (uhe: UnknownHostException) {
            errorMessage.postValue(noConnection)
        } catch (exception: Exception) {
            errorMessage.postValue(exception.localizedMessage)
        }
    }

    suspend fun getLast(lastMovieId: Long) {
        try {
            val movieDetails = movieService.getMovieDetails(lastMovieId).await()
            randomMovie.postValue(movieDetails)
        } catch (httpException: HttpException) {
            errorMessage.postValue(httpException.message())
        } catch (uhe: UnknownHostException) {
            errorMessage.postValue(noConnection)
        } catch (exception: Exception) {
            errorMessage.postValue(exception.localizedMessage)
        }
    }

    fun observeErrorMsg(): LiveData<String> {
        return errorMessage
    }

    fun observeMovieDetails(): LiveData<MovieDetails> {
        return randomMovie
    }
}