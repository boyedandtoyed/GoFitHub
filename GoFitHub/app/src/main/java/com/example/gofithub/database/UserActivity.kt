package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_activities")
data class UserActivity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val activityStartDate: String,
    val activityCompletedDate: String? = null,
    val activityId: Int,
    val activityName: String,

    val activityCompleted: Boolean,
    val duration: Int,
    val heartRate: Int,
    val speed: Int,
    val distance: Int,
    val caloriesBurned: Int,

)
