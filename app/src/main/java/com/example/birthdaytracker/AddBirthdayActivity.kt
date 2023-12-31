package com.example.birthdaytracker

import android.app.DatePickerDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.birthdaytracker.model.BirthdayModel
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddBirthdayActivity : AppCompatActivity() {
    private var selectedDate: String = ""
    lateinit var dateButton: Button
    lateinit var saveButton: Button
    lateinit var nameEditText: TextInputEditText
    lateinit var labelEditText: TextInputEditText
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_birthday)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dateButton = findViewById(R.id.selectDateButton)
        saveButton = findViewById(R.id.saveButton)
        nameEditText = findViewById(R.id.nameEditText)
        labelEditText = findViewById(R.id.labelEditText)

        dateButton.setOnClickListener {
            showDatePickerDialog()
        }

        saveButton.setOnClickListener {
            saveBirthday()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.CANADA)
            selectedDate = dateFormat.format(calendar.time)
            dateButton.text = dateFormat.format(calendar.time)
        }
        DatePickerDialog(
            this, dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveBirthday() {
        val personName = nameEditText.text.toString()
        val label = labelEditText.text.toString()

        if (personName.isNotEmpty() && label.isNotEmpty() && selectedDate.isNotEmpty()) {
            val birthday = BirthdayModel(personName, label, selectedDate)
            saveToSharedPreferences(birthday)
        } else {
            Toast.makeText(this, "Enter all dates", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToSharedPreferences(birthday: BirthdayModel) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("birthDaySharedPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val birthdayList: MutableList<BirthdayModel> = getBirthdaysFromSharedPreferences()
        birthdayList.add(birthday)

        editor.putString("birthdayList", Gson().toJson(birthdayList))
        editor.apply()

        Toast.makeText(this, "Data Added successfully", Toast.LENGTH_SHORT).show()
    }

    private fun getBirthdaysFromSharedPreferences(): MutableList<BirthdayModel> {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("birthDaySharedPref", MODE_PRIVATE)
        val birthdayListJson = sharedPreferences.getString("birthdayList", "")

        return if (birthdayListJson.isNullOrEmpty()) {
            mutableListOf()
        } else {
            Gson().fromJson(
                birthdayListJson,
                object : TypeToken<MutableList<BirthdayModel>>() {}.type
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
