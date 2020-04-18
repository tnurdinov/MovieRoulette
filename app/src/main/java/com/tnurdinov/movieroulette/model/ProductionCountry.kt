package com.tnurdinov.movieroulette.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountry(val iso_3166_1: String? = null, val name: String? = null)