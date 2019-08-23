package com.tnurdinov.movieroulette.model

class MovieShort(
        val vote_count: Int,
        val id: Long,
        val video: Boolean,
        val vote_average: Double,
        val title: String,
        val popularity: Double,
        val poster_path: String,
        val original_language: String,
        val original_title: String,
        val genre_ids: List<Int>,
        val backdrop_path: String,
        val adult: Boolean,
        val overview: String,
        val release_date: String
)

