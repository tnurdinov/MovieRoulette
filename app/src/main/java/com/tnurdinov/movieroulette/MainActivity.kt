package com.tnurdinov.movieroulette

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.tnurdinov.movieroulette.Constants.LAST_MOVIE_ID
import com.tnurdinov.movieroulette.Constants.PREFS_FILENAME
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_FROM
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_TILL
import com.tnurdinov.movieroulette.model.MovieDetails
import com.tnurdinov.movieroulette.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var circularProgressDrawable: CircularProgressDrawable? = null
    private lateinit var  picasso: Picasso


    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)
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
            viewModel.getRandomMovie()
        }

        sharedPreference.getLong(LAST_MOVIE_ID, 0).let { lastMovieId ->
            viewModel.requestMovieToShow(lastMovieId)
        }

        val releaseDateFrom = sharedPreference.getString(RELEASE_DATE_FROM, "")
        val releaseDateTill = sharedPreference.getString(RELEASE_DATE_TILL, "")



        initPicasso()

        initCircularDrawable()

        observeMovieDetail()
        observeError()
    }

    private fun initPicasso() {
        val okHttpClient = HttpClientHolder.client
        picasso = Picasso.Builder(this)
                .downloader(OkHttp3Downloader(okHttpClient))
                .build()
    }

    private fun initCircularDrawable() {
        circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable?.strokeWidth = 5f
        circularProgressDrawable?.centerRadius = 100f
        circularProgressDrawable?.start()
    }

    private fun observeMovieDetail() {
        val observer = Observer<MovieDetails> { movie ->
            sharedPreference.edit().putLong(LAST_MOVIE_ID, movie?.id ?: 0).apply()
            picasso.load("${BuildConfig.TMDB_IMG_URL}w780${movie?.backdrop_path}")
                    .placeholder(circularProgressDrawable!!)
                    .into(movie_backdrop)
            picasso.load("${BuildConfig.TMDB_IMG_URL}w342${movie?.poster_path}")
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
