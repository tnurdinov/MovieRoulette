package com.tnurdinov.movieroulette

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ThemoviedbService {
    @GET("3/movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY, @Query("page") page: Int = 1) : Deferred<TopRatedReponse>

    @GET("3/movie/{id}")
    fun getMovieDetails(@Path("id") id: Long, @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): Deferred<MovieDetails>

    companion object {
        fun create(): ThemoviedbService {

            val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .baseUrl(BuildConfig.TMDB_BASE_URL)
                    .build()

            return retrofit.create(ThemoviedbService::class.java)
        }
    }
}