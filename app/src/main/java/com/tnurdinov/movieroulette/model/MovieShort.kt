package com.tnurdinov.movieroulette.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieShort(
        val vote_count: Int = 0,
        val id: Long = 0,
        val video: Boolean = false,
        val vote_average: Double = 0.0,
        val title: String? = null,
        val popularity: Double = 0.0,
        val poster_path: String? = null,
        val original_language: String? = null,
        val original_title: String? = null,
        val genre_ids: List<Int>? = null,
        val backdrop_path: String? = null,
        val adult: Boolean = false,
        val overview: String? = null,
        val release_date: String? = null
)

