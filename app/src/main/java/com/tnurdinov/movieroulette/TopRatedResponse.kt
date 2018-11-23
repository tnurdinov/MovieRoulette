package com.tnurdinov.movieroulette

import com.squareup.moshi.JsonClass
import com.tnurdinov.movieroulette.model.MovieShort

@JsonClass(generateAdapter = true)
data class TopRatedResponse(val page: Int = 0,
                            val total_results: Int = 0,
                            val total_pages: Int = 0,
                            val results: List<MovieShort>? = null)