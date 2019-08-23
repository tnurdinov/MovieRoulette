package com.tnurdinov.movieroulette

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.tnurdinov.movieroulette.Constants.LAST_MOVIE_ID
import com.tnurdinov.movieroulette.Constants.PREFS_FILENAME
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_FROM
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_TILL
import com.tnurdinov.movieroulette.model.MovieDetails
import com.tnurdinov.movieroulette.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private val sharedPreference: SharedPreferences by lazy {
        this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bar.replaceMenu(R.menu.main)

        bar.setNavigationOnClickListener {
            // do something interesting on navigation click
        }

        bar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.filter -> {
                    startActivity(Intent(this@MainActivity, FilterActivity::class.java))
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener true
        }

        fab.setOnClickListener {
            viewModel.requestRandomMovie()
        }

        sharedPreference.getLong(LAST_MOVIE_ID, 0).let { lastMovieId ->
            viewModel.requestMovieToShow(lastMovieId)
        }

        val releaseDateFrom = sharedPreference.getString(RELEASE_DATE_FROM, "")
        val releaseDateTill = sharedPreference.getString(RELEASE_DATE_TILL, "")


        observeMovieDetail()
        observeErrorMessage()
    }

    private fun observeMovieDetail() {
        val observer = Observer<MovieDetails> { movie ->
            sharedPreference.edit().putLong(LAST_MOVIE_ID, movie?.id ?: 0).apply()
            movie_backdrop.load("${BuildConfig.TMDB_IMG_URL}w780${movie?.backdrop_path}")
            movie_poster.load("${BuildConfig.TMDB_IMG_URL}w342${movie?.poster_path}")
            movie_name.text = movie?.title
            movie_year.text = String.format(getString(R.string.release_date), movie?.release_date)
            movie_rating.text = String.format(getString(R.string.rating), movie?.vote_average)
            movie_description.text = movie?.overview
        }
        viewModel.getObservableMovieDetail().observe(this, observer)
    }

    private fun observeErrorMessage() {
        val observer = Observer<String> { errorMessage ->
            Snackbar.make(root_view, errorMessage, LENGTH_SHORT).show()
        }
        viewModel.getObservableErrorMsg().observe(this, observer)
    }
}
