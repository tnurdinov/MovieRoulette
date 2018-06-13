package com.tnurdinov.movieroulette

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private var poster: ImageView? = null
    private var name: TextView? = null
    private var year: TextView? = null
    private var rating:TextView? = null
    private var description: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        poster = findViewById(R.id.movie_poster)
        name = findViewById(R.id.movie_name)
        year = findViewById(R.id.movie_year)
        rating = findViewById(R.id.movie_rating)
        description = findViewById(R.id.movie_description)

        launch(UI) {
            try {
                val ratedReponse = ThemoviedbService.create().getTopRatedMovies().await()
                val movies = ratedReponse.results
                val randomMovieId = movies?.get(Random().nextInt(movies.size - 1))?.id!!
                val firstMovie = ThemoviedbService.create().getMovieDetails(randomMovieId).await()
                Log.d("TAG", firstMovie.poster_path)
                Picasso.get().load(BuildConfig.TMDB_IMG_URL + "w780${firstMovie.backdrop_path}").into(poster)
                name?.text = firstMovie.title
                year?.text = String.format(getString(R.string.release_date), firstMovie.release_date)
                rating?.text = String.format(getString(R.string.rating), firstMovie.vote_average)
                description?.text = firstMovie.overview

            } catch (e: Exception) {
                Log.e("TAG", e.localizedMessage)
            }
        }
    }
}
