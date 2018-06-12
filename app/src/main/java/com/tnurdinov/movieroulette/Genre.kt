package com.tnurdinov.movieroulette

import com.squareup.moshi.Json


data class Genre(
    @Json(name = "id") var id: Int = 0,
    @Json(name = "name") var name: String? = null
)