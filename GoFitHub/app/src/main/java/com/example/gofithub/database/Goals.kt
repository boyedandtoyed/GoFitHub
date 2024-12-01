package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_goals")
data class Goals(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val goalCompleted: Boolean?=false,
    val goalCompletedDate: String,

    val goalText: String,

    val duration: Int,
    val caloriesTarget: Int,

    )