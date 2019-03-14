package com.tnurdinov.movieroulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tnurdinov.movieroulette.MovieResult
import com.tnurdinov.movieroulette.model.MovieDetails
import com.tnurdinov.movieroulette.repository.MovieRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MovieViewModel : ViewModel(), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val randomMovie: MutableLiveData<MovieDetails> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()

    private val repository by lazy {
        MovieRepository()
    }

    fun requestRandomMovie() = launch {
        val result = withContext(Dispatchers.IO) {
            repository.getRandomMovie()
        }
        when (result) {
            is MovieResult.Success -> randomMovie.postValue(result.details)
            is MovieResult.Error -> errorMessage.postValue(result.error.localizedMessage)
        }
    }

    private fun requestLastMovie(movieId: Long) = launch {
        val lastMovie = withContext(Dispatchers.IO) {
            repository.getLast(movieId)
        }
        when (lastMovie) {
            is MovieResult.Success -> randomMovie.postValue(lastMovie.details)
            is MovieResult.Error -> errorMessage.postValue(lastMovie.error.localizedMessage)
        }
    }

    fun getObservableMovieDetail(): LiveData<MovieDetails> {
        return randomMovie
    }

    fun getObservableErrorMsg(): LiveData<String> {
        return errorMessage
    }

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    fun requestMovieToShow(lastMovieId: Long) {
        when(lastMovieId) {
            0L -> requestRandomMovie()
            else -> requestLastMovie(lastMovieId)
        }
    }
}