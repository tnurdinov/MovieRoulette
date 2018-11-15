package com.tnurdinov.movieroulette.model

data class ProductionCompany(
        var id: Int = 0,
        var logo_path: String? = null,
        var name: String? = null,
        var origin_country: String? = null
)