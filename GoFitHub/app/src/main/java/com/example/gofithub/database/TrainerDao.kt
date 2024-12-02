package com.example.gofithub.database

import androidx.room.*

@Dao
interface TrainerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainer(trainer: Trainer): Long

    @Query("SELECT * FROM trainer_info WHERE email = :email")
    suspend fun getTrainerByEmail(email: String): Trainer?

    @Query("SELECT * FROM trainer_info WHERE id = :id")
    suspend fun getTrainerById(id: Int): Trainer

    //get all trainers that match the list of ids
    @Query("SELECT * FROM trainer_info WHERE id IN (:trainerIds)")
    suspend fun getTrainersByIds(trainerIds: List<Int>): List<Trainer>


    @Query("SELECT * FROM trainer_info")
    suspend fun getAllTrainers(): List<Trainer>

    //get ALL TRAINERS EXCEPT SPECIFIED trainers list
    @Query("SELECT * FROM trainer_info WHERE id NOT IN (:trainerIds)")
    suspend fun getAllTrainersExcept(trainerIds: List<Int>): List<Trainer>

    @Query("SELECT COUNT(*) FROM trainer_info WHERE email = :email")
    suspend fun isEmailExists(email: String): Int

    @Update
    suspend fun updateTrainer(trainer: Trainer)

    @Delete
    suspend fun deleteTrainer(trainer: Trainer)

    @Query("UPDATE trainer_info SET password = :newPassword WHERE email = :email")
    suspend fun updatePassword(email: String, newPassword: String)

    @Query("UPDATE trainer_info SET isAvailable = :isAvailable WHERE id = :trainerId")
    suspend fun updateAvailability(trainerId: Int, isAvailable: Boolean)

    @Query("UPDATE trainer_info SET ratingOutofFive = :rating, numberOfRatings = :numberOfRatings WHERE id = :trainerId")
    suspend fun updateRating(trainerId: Int, rating: Double, numberOfRatings: Int)

    @Query("SELECT * FROM trainer_info WHERE isAvailable = 1")
    suspend fun getAvailableTrainers(): List<Trainer>

    @Query("SELECT * FROM trainer_info WHERE ratingOutofFive >= :minRating ORDER BY ratingOutofFive DESC")
    suspend fun getTopRatedTrainers(minRating: Double): List<Trainer>

    @Query("SELECT * FROM trainer_info WHERE specialty = :specialty")
    suspend fun getTrainersBySpecialty(specialty: String): List<Trainer>

    //get trainer ratings by trainerid
    @Query("SELECT ratingOutofFive FROM trainer_info WHERE id = :trainerId")
    suspend fun getTrainerRatings(trainerId: Int): Double

}