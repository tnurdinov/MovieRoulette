package com.tnurdinov.movieroulette

import com.tnurdinov.movieroulette.model.MovieDetails

sealed class MovieResult {
    class Success(val details: MovieDetails) : MovieResult()
    class Error(val error: Throwable) : MovieResult()
}