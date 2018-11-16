package com.tnurdinov.movieroulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tnurdinov.movieroulette.model.MovieDetails
import com.tnurdinov.movieroulette.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

    fun getRandomMovie() {
        try {
            launch {
                randomMovie.postValue(repository.getRandomMovie())
            }
        } catch (e: Throwable) {
            errorMessage.postValue(e.localizedMessage)
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
        job.cancel()
    }
}