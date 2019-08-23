package com.tnurdinov.movieroulette.model

import com.squareup.moshi.JsonClass
import com.tnurdinov.movieroulette.model.Genre
import com.tnurdinov.movieroulette.model.ProductionCompany
import com.tnurdinov.movieroulette.model.ProductionCountry
import com.tnurdinov.movieroulette.model.SpokenLanguage

@JsonClass(generateAdapter = true)
class MovieDetails(
        val adult: Boolean? = false,
        val backdrop_path: String? = null,
        val belongs_to_collection: Any? = null,
        val budget: Int? = 0,
        val genres: List<Genre>? = null,
        val homepage: Any? = null,
        val id: Long? = 0,
        val imdb_id: String? = null,
        val original_language: String? = null,
        val original_title: String? = null,
        val overview: String? = null,
        val popularity: Double?,
        val poster_path: String? = null,
        val production_companies: List<ProductionCompany>? = null,
        val production_countries: List<ProductionCountry>? = null,
        val release_date: String? = null,
        val revenue: Int? = 0,
        val runtime: Int? = 0,
        val spoken_languages: List<SpokenLanguage>? = null,
        val status: String? = null,
        val tagline: String? = null,
        val title: String? = null,
        val video: Boolean? = false,
        val vote_average: Double?,
        val vote_count: Int? = 0
)