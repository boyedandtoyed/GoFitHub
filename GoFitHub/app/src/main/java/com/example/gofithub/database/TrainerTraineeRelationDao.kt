package com.example.gofithub.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrainerTraineeRelationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelation(relation: TrainerTraineeRelation)

    //retrieve trainees for a trainer using trainer id
    @Query("SELECT * FROM trainer_trainee_relation WHERE trainerId = :trainerId")
    suspend fun getTraineesForTrainer(trainerId: Int): List<TrainerTraineeRelation>

    //get pais traners for users
    @Query("SELECT * FROM trainer_trainee_relation WHERE traineeId = :traineeId")
    suspend fun getTrainersForTrainee(traineeId: Int): List<TrainerTraineeRelation>


}