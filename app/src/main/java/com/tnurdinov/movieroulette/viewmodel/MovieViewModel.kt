package com.tnurdinov.movieroulette.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tnurdinov.movieroulette.MovieDetails
import com.tnurdinov.movieroulette.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieViewModel: ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val randomMovie: MutableLiveData<MovieDetails> = MutableLiveData()

    private val repository: MovieRepository = MovieRepository()

    fun getRandomMovie() {
        launch {
            randomMovie.postValue(repository.getRandomMovie())
        }
    }

    fun observeMovie():LiveData<MovieDetails> {
        return randomMovie
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}