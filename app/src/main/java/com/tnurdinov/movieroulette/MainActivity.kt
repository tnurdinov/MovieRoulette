package com.tnurdinov.movieroulette

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.tnurdinov.movieroulette.model.MovieDetails
import com.tnurdinov.movieroulette.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var poster: ImageView
    private lateinit var name: TextView
    private lateinit var year: TextView
    private lateinit var rating: TextView
    private lateinit var description: TextView
    private lateinit var fab: FloatingActionButton
    private var circularProgressDrawable:CircularProgressDrawable? = null
    private val PREFS_FILENAME = "com.teamtreehouse.colorsarefun.prefs"
    private val LAST_MOVIE_ID = "last_movie_id"


    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)
    }

    private val sharedPreference: SharedPreferences by lazy {
        this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        poster = findViewById(R.id.movie_poster)
        name = findViewById(R.id.movie_name)
        year = findViewById(R.id.movie_year)
        rating = findViewById(R.id.movie_rating)
        description = findViewById(R.id.movie_description)
        fab = findViewById(R.id.fab)

        fab.setOnClickListener {
            viewModel.getRandomMovie()
        }

        circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable?.strokeWidth = 5f
        circularProgressDrawable?.centerRadius = 100f
        circularProgressDrawable?.start()

        sharedPreference.getLong(LAST_MOVIE_ID, 0).let { lastMovieId ->
            when(lastMovieId) {
                0L -> viewModel.getRandomMovie()
                else -> viewModel.showLastMovie(lastMovieId)
            }
        }

        observeMovieDetail()
        observeError()
    }

    private fun observeMovieDetail() {

        val options = RequestOptions()
                .optionalCenterCrop()
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

        val observer = Observer<MovieDetails> { movie ->
            sharedPreference.edit().putLong(LAST_MOVIE_ID, movie?.id ?: 0).apply()
            Glide.with(this)
                    .load("${BuildConfig.TMDB_IMG_URL}w780${movie?.backdrop_path}")
                    .apply(options)
                    .into(poster)
            name.text = movie?.title
            year.text = String.format(getString(R.string.release_date), movie?.release_date)
            rating.text = String.format(getString(R.string.rating), movie?.vote_average)
            description.text = movie?.overview
        }
        viewModel.observeMovieDetails().observe(this, observer)
    }

    private fun observeError() {
        val observer = Observer<String> { errorMessage ->
            Snackbar.make(poster, errorMessage, LENGTH_SHORT).show()
        }


        viewModel.observeError().observe(this, observer)
    }
}
