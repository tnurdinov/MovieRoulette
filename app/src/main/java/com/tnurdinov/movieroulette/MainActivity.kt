package com.tnurdinov.movieroulette

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tnurdinov.movieroulette.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var poster: ImageView
    private lateinit var name: TextView
    private lateinit var year: TextView
    private lateinit var rating:TextView
    private lateinit var description: TextView
    private val viewModel: MovieViewModel = MovieViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        poster = findViewById(R.id.movie_poster)
        name = findViewById(R.id.movie_name)
        year = findViewById(R.id.movie_year)
        rating = findViewById(R.id.movie_rating)
        description = findViewById(R.id.movie_description)

        val options = RequestOptions().optionalCenterCrop()

        viewModel.getRandomMovie()

        viewModel.observeMovie().observe(this, Observer { movie ->
            Glide.with(this).load("${BuildConfig.TMDB_IMG_URL}w780${movie?.backdrop_path}").apply(options).into(poster)
            name.text = movie?.title
            year.text = String.format(getString(R.string.release_date), movie?.release_date)
            rating.text = String.format(getString(R.string.rating), movie?.vote_average)
            description.text = movie?.overview
        })
    }
}
