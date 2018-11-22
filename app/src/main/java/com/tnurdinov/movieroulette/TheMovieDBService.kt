package com.tnurdinov.movieroulette

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tnurdinov.movieroulette.model.MovieDetails
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBService {
    @GET("3/movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY, @Query("page") page: Int = 1) : Deferred<TopRatedResponse>

    @GET("3/movie/{id}")
    fun getMovieDetails(@Path("id") id: Long, @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): Deferred<MovieDetails>

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