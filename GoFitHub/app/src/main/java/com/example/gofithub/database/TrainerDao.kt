package com.example.gofithub.database

import androidx.room.*

@Dao
interface TrainerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainer(trainer: Trainer): Long

    @Query("SELECT * FROM trainer_info WHERE email = :email")
    suspend fun getTrainerByEmail(email: String): Trainer?

    @Query("SELECT * FROM trainer_info WHERE id = :id")
    suspend fun getTrainerById(id: Int): Trainer?

    @Query("SELECT * FROM trainer_info")
    suspend fun getAllTrainers(): List<Trainer>

    @Query("SELECT COUNT(*) FROM trainer_info WHERE email = :email")
    suspend fun isEmailExists(email: String): Int

    @Update
    suspend fun updateTrainer(trainer: Trainer)

    @Delete
    suspend fun deleteTrainer(trainer: Trainer)

}