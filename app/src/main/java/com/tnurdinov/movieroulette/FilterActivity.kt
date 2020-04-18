package com.tnurdinov.movieroulette

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.tnurdinov.movieroulette.Constants.PREFS_FILENAME
import com.tnurdinov.movieroulette.Constants.RATING
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_FROM
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_TILL
import com.tnurdinov.movieroulette.databinding.ActivityFilterBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    private val sharedPreference: SharedPreferences by lazy {
        this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.filterToolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
            setTitle(R.string.filters)
        }

        val fromDateString = sharedPreference.getString(RELEASE_DATE_FROM, "1900-01-01")!!
        val tillDateString = sharedPreference.getString(RELEASE_DATE_TILL, getDateFormat().format(Date().time))!!
        val ratingValue = sharedPreference.getInt(RATING, 8)


        updateFromLabel(fromDateString)
        updateToLabel(tillDateString)


        val fromDatePickerListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val dateText = getDateFormat().format(calendar.time)
            updateFromLabel(dateText)
            saveFromDate(dateText)
        }

        val toDatePickerListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val dateText = getDateFormat().format(calendar.time)
            updateToLabel(dateText)
            saveTillDate(dateText)
        }

        binding.fromDateTv.setOnClickListener {
            val cal = getCalendarWithDate(fromDateString)
            val dialog = DatePickerDialog(
                    this@FilterActivity,
                    fromDatePickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = Date().time
            dialog.show()
        }

        binding.toDateTv.setOnClickListener {
            val cal = getCalendarWithDate(tillDateString)
            val dialog = DatePickerDialog(
                    this@FilterActivity, toDatePickerListener, cal
                    
                    .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = Date().time
            dialog.show()
        }

        binding.ratingSeek.progress = ratingValue
        updateRatingLabel(ratingValue)

        binding.ratingSeek.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateRatingLabel(progress)
                saveRating(progress)
            }
        })

    }

    private fun updateRatingLabel(rate: Int) {
        val minRateText = getString(R.string.minimum_rating, rate.toString())
        binding.rating.text = minRateText
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun updateFromLabel(dateText: String) {
        binding.fromDateTv.text = dateText
    }

    private fun updateToLabel(dateText: String) {
        binding.toDateTv.text = dateText
    }

    private fun getDateFormat(): SimpleDateFormat {
        val dateFormat = getString(R.string.date_format)
        return SimpleDateFormat(dateFormat, Locale.US)
    }

    private fun getCalendarWithDate(dateStr: String) = Calendar.getInstance().apply {
        time = getDateFormat().parse(dateStr)
    }

    private fun saveFromDate(fromDate: String) = sharedPreference.edit {
        putString(RELEASE_DATE_FROM, fromDate)
    }

    private fun saveTillDate(tillDate: String) = sharedPreference.edit {
        putString(RELEASE_DATE_TILL, tillDate)
    }

    private fun saveRating(rate: Int) = sharedPreference.edit {
        putInt(RATING, rate)
    }
}
