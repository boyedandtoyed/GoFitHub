package com.example.gofithub.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainer_trainee_relation")
data class TrainerTraineeRelation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val trainerId: Int,
    val traineeId: Int,
    val paymentDate: String? = null,
    val paymentAmount: Double? = null,
    val trainingName: String? = null
)
