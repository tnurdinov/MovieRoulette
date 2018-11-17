package com.tnurdinov.movieroulette.model

import com.squareup.moshi.JsonClass
import com.tnurdinov.movieroulette.model.Genre
import com.tnurdinov.movieroulette.model.ProductionCompany
import com.tnurdinov.movieroulette.model.ProductionCountry
import com.tnurdinov.movieroulette.model.SpokenLanguage

@JsonClass(generateAdapter = true)
data class MovieDetails(

        var adult: Boolean? = false,
        var backdrop_path: String? = null,
        var belongs_to_collection: Any? = null,
        var budget: Int? = 0,
        var genres: List<Genre>? = null,
        var homepage: Any? = null,
        var id: Long? = 0,
        var imdb_id: String? = null,
        var original_language: String? = null,
        var original_title: String? = null,
        var overview: String? = null,
        var popularity: Double?,
        var poster_path: String? = null,
        var production_companies: List<ProductionCompany>? = null,
        var production_countries: List<ProductionCountry>? = null,
        var release_date: String? = null,
        var revenue: Int? = 0,
        var runtime: Int? = 0,
        var spoken_languages: List<SpokenLanguage>? = null,
        var status: String? = null,
        var tagline: String? = null,
        var title: String? = null,
        var video: Boolean? = false,
        var vote_average: Double?,
        var vote_count: Int? = 0

)