package com.tnurdinov.movieroulette

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopRatedReponse(
    @Json(name = "page") val page: Int = 0,
    @Json(name = "total_results") val totalResults: Int = 0,
    @Json(name = "total_pages") val totalPages: Int = 0,
    @Json(name = "results") val results: List<Result>? = null
)