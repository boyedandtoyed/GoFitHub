package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainer_reviews")
data class TrainerReview(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val reviewText: String,
    val rating: Int,
    val reviewDate: String,
    val raterName: String,
    val raterUserId: Int,
    val trainerUserId: Int
)
