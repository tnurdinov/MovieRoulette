package com.tnurdinov.movieroulette

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopRatedReponse(val page: Int = 0, val total_results: Int = 0, val total_pages: Int = 0, val results: List<Result>? = null)