package com.tnurdinov.movieroulette

import com.tnurdinov.movieroulette.model.MovieDetails
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesAsync(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY, @Query("page") page: Int = 1) : TopRatedResponse

    @GET("movie/{id}")
    suspend fun getMovieDetailsAsync(@Path("id") id: Long, @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): MovieDetails

    @GET("discover/movie")
    suspend fun discoverMoviesAsync(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY, @Query("page") page: Int = 1, @Query("release_date.gte") releaseDateFrom: String = "1860-01-01", @Query("release_date.lte") releaseDateTill: String = "2019-01-30") : TopRatedResponse

    companion object {
        fun create(): TheMovieDBService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(HttpClientHolder.client)
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .build()

            return retrofit.create(TheMovieDBService::class.java)
        }
    }
}