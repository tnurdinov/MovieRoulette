package com.tnurdinov.movieroulette.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(val id: Int = 0, val name: String? = null)