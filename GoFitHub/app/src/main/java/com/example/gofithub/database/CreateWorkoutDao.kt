package com.example.gofithub.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gofithub.database.CreateWorkout

@Dao
interface CreateWorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: CreateWorkout): Long

    @Query("SELECT * FROM create_workout_table WHERE trainerId = :trainerId")
    suspend fun getWorkoutsByTrainerId(trainerId: Int): List<CreateWorkout>



}