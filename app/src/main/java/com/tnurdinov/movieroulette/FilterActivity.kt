package com.tnurdinov.movieroulette

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.tnurdinov.movieroulette.Constants.PREFS_FILENAME
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_FROM
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_TILL
import kotlinx.android.synthetic.main.activity_filter.*
import java.text.SimpleDateFormat
import java.util.*

class FilterActivity : AppCompatActivity() {

    private val calendar = Calendar.getInstance()

    private val sharedPreference: SharedPreferences by lazy {
        this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val fromDatePickerListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateFromLabel()
        }

        val toDatePickerListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateToLabel()
        }

        from_date_tv.setOnClickListener {
            DatePickerDialog(
                    this@FilterActivity, fromDatePickerListener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        to_date_tv.setOnClickListener {
            DatePickerDialog(
                    this@FilterActivity, toDatePickerListener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateFromLabel() {
        val myFormat = "yyyy-MM-DD"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val formattedText = sdf.format(calendar.time)
        from_date_tv.text = formattedText
        setFromDate(formattedText)
    }

    private fun updateToLabel() {
        val myFormat = "yyyy-MM-DD"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val formattedText = sdf.format(calendar.time)
        to_date_tv.text = formattedText
        setTillDate(formattedText)
    }

    private fun setFromDate(fromDate: String) {
        sharedPreference.edit {
            putString(RELEASE_DATE_FROM, fromDate)
        }
    }

    private fun setTillDate(tillDate: String) {
        sharedPreference.edit {
            putString(RELEASE_DATE_TILL, tillDate)
        }
    }
}
