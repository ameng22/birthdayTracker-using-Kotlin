package com.example.birthdaytracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var birthdayButton: Button
    lateinit var viewButton: Button
    lateinit var settingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        birthdayButton = findViewById(R.id.addButton)
        viewButton = findViewById(R.id.viewButton)
        settingsButton = findViewById(R.id.settingsButton)

        birthdayButton.setOnClickListener {
            startActivity(Intent(this, AddBirthdayActivity::class.java))
        }

        viewButton.setOnClickListener {
            startActivity(Intent(this, ListBirthdayActivity::class.java))
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
