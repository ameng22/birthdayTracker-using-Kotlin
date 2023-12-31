package com.example.birthdaytracker.model

import java.io.Serializable

data class BirthdayModel(
                        val personName: String,
                         val label: String,
                         val date: String): Serializable
