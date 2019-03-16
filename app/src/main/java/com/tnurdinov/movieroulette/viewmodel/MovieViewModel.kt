package com.tnurdinov.movieroulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tnurdinov.movieroulette.MovieResult
import com.tnurdinov.movieroulette.model.MovieDetails
import com.tnurdinov.movieroulette.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel : ViewModel() {
    private val randomMovie: MutableLiveData<MovieDetails> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()

    private val repository by lazy {
        MovieRepository()
    }

    fun requestRandomMovie() = viewModelScope.launch {
        val result = withContext(Dispatchers.IO) {
            repository.getRandomMovie()
        }
        when (result) {
            is MovieResult.Success -> randomMovie.postValue(result.details)
            is MovieResult.Error -> errorMessage.postValue(result.error.localizedMessage)
        }
    }

    private fun requestLastMovie(movieId: Long) = viewModelScope.launch {
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

    fun requestMovieToShow(lastMovieId: Long) {
        when(lastMovieId) {
            0L -> requestRandomMovie()
            else -> requestLastMovie(lastMovieId)
        }
    }
}