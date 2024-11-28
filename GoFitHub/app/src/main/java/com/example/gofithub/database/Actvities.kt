package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Actvities(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int?= null,
    val activityName: String,
)
