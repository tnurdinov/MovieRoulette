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
        val rMovie = repository.getRandomMovie()
        when (rMovie) {
            is MovieResult.Success -> randomMovie.postValue(rMovie.details)
            is MovieResult.Error -> errorMessage.postValue(rMovie.error.localizedMessage)
        }
    }

    fun showLastMovie(lastMovieId: Long) = launch {
        val lastMovie = repository.getLast(lastMovieId)
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
}