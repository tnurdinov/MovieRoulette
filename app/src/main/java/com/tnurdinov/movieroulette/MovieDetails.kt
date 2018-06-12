package com.tnurdinov.movieroulette

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetails(

    @Json(name = "adult")
    var adult: Boolean = false,
    @Json(name = "backdrop_path")
    var backdropPath: String? = null,
    @Json(name = "belongs_to_collection")
    var belongsToCollection: Any? = null,
    @Json(name = "budget")
    var budget: Int = 0,
    @Json(name = "genres")
    var genres: List<Genre>? = null,
    @Json(name = "homepage")
    var homepage: Any? = null,
    @Json(name = "id")
    var id: Int = 0,
    @Json(name = "imdb_id")
    var imdbId: String? = null,
    @Json(name = "original_language")
    var originalLanguage: String? = null,
    @Json(name = "original_title")
    var originalTitle: String? = null,
    @Json(name = "overview")
    var overview: String? = null,
    @Json(name = "popularity")
    var popularity: Double,
    @Json(name = "poster_path")
    var posterPath: String? = null,
    @Json(name = "production_companies")
    var productionCompanies: List<ProductionCompany>? = null,
    @Json(name = "production_countries")
    var productionCountries: List<ProductionCountry>? = null,
    @Json(name = "release_date")
    var releaseDate: String? = null,
    @Json(name = "revenue")
    var revenue: Int = 0,
    @Json(name = "runtime")
    var runtime: Int = 0,
    @Json(name = "spoken_languages")
    var spokenLanguages: List<SpokenLanguage>? = null,
    @Json(name = "status")
    var status: String? = null,
    @Json(name = "tagline")
    var tagline: String? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "video")
    var video: Boolean = false,
    @Json(name = "vote_average")
    var voteAverage: Double,
    @Json(name = "vote_count")
    var voteCount: Int = 0

)