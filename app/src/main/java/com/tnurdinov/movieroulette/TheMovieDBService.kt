package com.tnurdinov.movieroulette

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tnurdinov.movieroulette.model.MovieDetails
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface TheMovieDBService {
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY, @Query("page") page: Int = 1) : Deferred<TopRatedResponse>

    @GET("movie/{id}")
    fun getMovieDetails(@Path("id") id: Long, @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): Deferred<MovieDetails>

    @GET("discover/movie")
    fun discoverMovies(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY, @Query("page") page: Int = 1, @Query("release_date.gte") releaseDateFrom: String = "1860-01-01", @Query("release_date.lte") releaseDateTill: String = "2019-01-30") : Deferred<TopRatedResponse>

    companion object {
        fun create(): TheMovieDBService {

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(HttpClientHolder.client)
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .build()

            return retrofit.create(TheMovieDBService::class.java)
        }
    }
}