package com.tnurdinov.movieroulette

import com.tnurdinov.movieroulette.model.MovieDetails

sealed class MovieResult {
    data class Success(val details: MovieDetails) : MovieResult()
    data class Error(val error: Throwable) : MovieResult()
}