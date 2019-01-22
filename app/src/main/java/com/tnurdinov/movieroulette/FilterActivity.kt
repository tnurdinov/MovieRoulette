package com.tnurdinov.movieroulette

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.tnurdinov.movieroulette.Constants.PREFS_FILENAME
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_FROM
import com.tnurdinov.movieroulette.Constants.RELEASE_DATE_TILL

class FilterActivity : AppCompatActivity() {

    private val sharedPreference: SharedPreferences by lazy {
        this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        sharedPreference.edit {
            putString(RELEASE_DATE_FROM, "2019-01-01")
            putString(RELEASE_DATE_TILL, "2019-01-20")
        }
    }
}
