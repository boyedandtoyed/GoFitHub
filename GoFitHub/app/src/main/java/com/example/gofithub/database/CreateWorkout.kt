package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "create_workout_table")
data class CreateWorkout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val youtubeLink: String,
    val trainerId: Int,

)
