package com.tnurdinov.movieroulette

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.tnurdinov.movieroulette.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private var poster: ImageView? = null
    private var name: TextView? = null
    private var year: TextView? = null
    private var rating:TextView? = null
    private var description: TextView? = null
    private val viewModel: MovieViewModel = MovieViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        poster = findViewById(R.id.movie_poster)
        name = findViewById(R.id.movie_name)
        year = findViewById(R.id.movie_year)
        rating = findViewById(R.id.movie_rating)
        description = findViewById(R.id.movie_description)


        viewModel.getRandomMovie()

        viewModel.observeMovie().observe(this, Observer { movie ->
            Picasso.get().load(BuildConfig.TMDB_IMG_URL + "w780${movie?.backdrop_path}").into(poster)
            name?.text = movie?.title
            year?.text = String.format(getString(R.string.release_date), movie?.release_date)
            rating?.text = String.format(getString(R.string.rating), movie?.vote_average)
            description?.text = movie?.overview
        })
    }
}
