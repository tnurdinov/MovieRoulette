package com.tnurdinov.movieroulette.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguage(val iso_639_1: String? = null, val name: String? = null)