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
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val randomMovie: MutableLiveData<MovieDetails> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()

    private val repository by lazy {
        MovieRepository()
    }

    fun getRandomMovie() = launch {
        val result = withContext(Dispatchers.IO) {
            repository.getRandomMovie()
        }
        when (result) {
            is MovieResult.Success -> randomMovie.postValue(result.details)
            is MovieResult.Error -> errorMessage.postValue(result.error.localizedMessage)
        }
    }

    private fun showLastMovie(lastMovieId: Long) = launch {
        val lastMovie = withContext(Dispatchers.IO) {
            repository.getLast(lastMovieId)
        }
        when (lastMovie) {
            is MovieResult.Success -> randomMovie.postValue(lastMovie.details)
            is MovieResult.Error -> errorMessage.postValue(lastMovie.error.localizedMessage)
        }
    }

    fun observeMovieDetails(): LiveData<MovieDetails> {
        return randomMovie
    }

    fun observeError(): LiveData<String> {
        return errorMessage
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun requestMovieToShow(lastMovieId: Long) {
        when(lastMovieId) {
            0L -> getRandomMovie()
            else -> showLastMovie(lastMovieId)
        }
    }
}