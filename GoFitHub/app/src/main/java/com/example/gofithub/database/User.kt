package com.example.gofithub.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String, // Ensure uniqueness
    val password: String,
    val dob: String,
    val weight: Double,
    val height: Double,
    val goalType: String,
    val goalExplanation: String? = null,
    val profilePicture: String? = null, // Store URI as a String
    val isSubscribed: Boolean? = false
)
