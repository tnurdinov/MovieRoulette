package com.tnurdinov.movieroulette

import com.squareup.moshi.Json


data class SpokenLanguage(
    @Json(name = "iso_639_1") var iso6391: String? = null,
    @Json(name = "name") var name: String? = null
)