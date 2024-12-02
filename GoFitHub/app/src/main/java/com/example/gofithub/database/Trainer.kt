package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainer_info")
data class Trainer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val certificate: String? = null,
    val experienceLevel: String,
    val specialty: String,
    val bio: String,
    val hourlyRate: Double,
    val profilePicture: String? = null, // Store URI as a String
    val isAvailable: Boolean = true,
    val ratingOutofFive: Double = 0.0,
    val numberOfRatings: Int = 0

)
