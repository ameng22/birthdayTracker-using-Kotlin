package com.example.birthdaytracker

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var resetBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        resetBtn=findViewById(R.id.reset_btn)

        resetBtn.setOnClickListener {
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("birthDaySharedPref", MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            editor.clear()

            editor.apply()
            Toast.makeText(this, "Data cleared successfully", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}