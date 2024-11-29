package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_activities")
data class UserActivity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val activityCompletedDate: String,
    val activityName: String,

    val duration: Int,
    val heartRate: Int,
    val speed: Int?=null,
    val distance: Int?=null,
    val caloriesBurned: Int,

)
