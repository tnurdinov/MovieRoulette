package com.tnurdinov.movieroulette

import com.squareup.moshi.Json

data class Result(
        @Json(name = "vote_count") val voteCount: Int,
        @Json(name = "id") val id: Long,
        @Json(name = "video") val video: Boolean,
        @Json(name = "vote_average") val voteAverage: Double,
        @Json(name = "title") val title: String,
        @Json(name = "popularity") val popularity: Double,
        @Json(name = "poster_path") val posterPath: String,
        @Json(name = "original_language") val originalLanguage: String,
        @Json(name = "original_title") val originalTitle: String,
        @Json(name = "genre_ids") val genreIds: List<Int>,
        @Json(name = "backdrop_path") val backdropPath: String,
        @Json(name = "adult") val adult: Boolean,
        @Json(name = "overview") val overview: String,
        @Json(name = "release_date") val releaseDate: String
)

