package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_users")
data class TopUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val goalCount: Int = 0, // For goal count query
    val totalCalories: Int = 0 // For calories burned query
)
