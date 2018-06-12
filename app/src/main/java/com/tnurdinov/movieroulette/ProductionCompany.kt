package com.tnurdinov.movieroulette

import com.squareup.moshi.Json

data class ProductionCompany(
    @Json(name = "id") var id: Int = 0,
    @Json(name = "logo_path") var logoPath: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "origin_country") var originCountry: String? = null
)