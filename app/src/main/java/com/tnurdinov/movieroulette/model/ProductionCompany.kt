package com.tnurdinov.movieroulette.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCompany(
        val id: Int = 0,
        val logo_path: String? = null,
        val name: String? = null,
        val origin_country: String? = null
)