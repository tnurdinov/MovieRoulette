package com.tnurdinov.movieroulette

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.tnurdinov.movieroulette.model.MovieDetails
import com.tnurdinov.movieroulette.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var circularProgressDrawable: CircularProgressDrawable? = null
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
                    .into(movie_backdrop)
            Glide.with(this)
                    .load("${BuildConfig.TMDB_IMG_URL}w342${movie?.poster_path}")
                    .into(movie_poster)
            movie_name.text = movie?.title
            movie_year.text = String.format(getString(R.string.release_date), movie?.release_date)
            movie_rating.text = String.format(getString(R.string.rating), movie?.vote_average)
            movie_description.text = movie?.overview
        }
        viewModel.observeMovieDetails().observe(this, observer)
    }

    private fun observeError() {
        val observer = Observer<String> { errorMessage ->
            Snackbar.make(root_view, errorMessage, LENGTH_SHORT).show()
        }


        viewModel.observeError().observe(this, observer)
    }
}
