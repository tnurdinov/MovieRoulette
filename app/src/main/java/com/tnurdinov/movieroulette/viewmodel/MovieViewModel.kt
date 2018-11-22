package com.tnurdinov.movieroulette.viewmodel

import androidx.lifecycle.LiveData
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

    private val repository by lazy {
        MovieRepository()
    }

    fun getRandomMovie() {
        launch {
            repository.getRandomMovie()
        }
    }

    fun showLastMovie(lastMovieId: Long) {
        launch {
            repository.getLast(lastMovieId)
        }
    }

    fun observeMovieDetails(): LiveData<MovieDetails> {
        return repository.observeMovieDetails()
    }

    fun observeError(): LiveData<String> {
        return repository.observeErrorMsg()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}