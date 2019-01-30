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

    private val sharedPreference: SharedPreferences by lazy {
        this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        from_date_tv.text = "1900-01-01"
        updateToLabel(getDateFormat().format(Date().time))


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

        from_date_tv.setOnClickListener {
            val cal = getCalendarWithDate("1900-01-01")
            DatePickerDialog(
                    this@FilterActivity, fromDatePickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        to_date_tv.setOnClickListener {
            val cal = getCalendarWithDate(getDateFormat().format(Date().time))
            DatePickerDialog(
                    this@FilterActivity, toDatePickerListener, cal
                    .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateFromLabel(dateText: String) {
        from_date_tv.text = dateText
    }

    private fun updateToLabel(dateText: String) {
        to_date_tv.text = dateText
    }

    private fun getDateFormat(): SimpleDateFormat {
        val dateFormat = "yyyy-MM-dd"
        return SimpleDateFormat(dateFormat, Locale.US)
    }

    private fun getCalendarWithDate(dateStr: String): Calendar {
        val date = getDateFormat().parse(dateStr)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    private fun saveFromDate(fromDate: String) {
        sharedPreference.edit {
            putString(RELEASE_DATE_FROM, fromDate)
        }
    }

    private fun saveTillDate(tillDate: String) {
        sharedPreference.edit {
            putString(RELEASE_DATE_TILL, tillDate)
        }
    }
}
