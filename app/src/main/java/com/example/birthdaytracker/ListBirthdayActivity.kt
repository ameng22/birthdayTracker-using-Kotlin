package com.example.birthdaytracker

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdaytracker.model.BirthdayAdapter
import com.example.birthdaytracker.model.BirthdayModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListBirthdayActivity : AppCompatActivity() {
    private val birthdays = mutableListOf<BirthdayModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var toolbar: Toolbar
    lateinit var birthdayAdapter: BirthdayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_birthday)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.recyclerView)

        loadBirthdaysFromSharedPreferences()

        birthdayAdapter = BirthdayAdapter(birthdays, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = birthdayAdapter
    }

    private fun loadBirthdaysFromSharedPreferences() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("birthDaySharedPref", MODE_PRIVATE)

        val birthdayListJson = sharedPreferences.getString("birthdayList", "")

        if (birthdayListJson.isNullOrEmpty()) {
            Toast.makeText(this, "No Data to load", Toast.LENGTH_SHORT)
            return
        }

        val savedBirthdays: MutableList<BirthdayModel> = Gson().fromJson(
            birthdayListJson,
            object : TypeToken<MutableList<BirthdayModel>>() {}.type
        )

        for (birthday in savedBirthdays) {
            Log.d("BirthdayTracker", "Loaded Birthday: $birthday")
        }

        Log.d("BirthdayTracker", "After loading: birthdays size: ${birthdays.size}")

        birthdays.clear()
        birthdays.addAll(savedBirthdays)

        Log.d("BirthdayTracker", "After loading: birthdays size: ${birthdays.size}")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
