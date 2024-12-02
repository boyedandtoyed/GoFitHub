package com.example.gofithub.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GoalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoals(goal: Goals): Long

    // Get user incomplete goals
    @Query("SELECT * FROM user_goals WHERE userId = :userId AND goalCompleted = 0")
    suspend fun getIncompleteGoals(userId: Int): List<Goals>

    // Set goal to completed
    @Query("UPDATE user_goals SET goalCompleted = 1 WHERE id = :goalId")
    suspend fun setGoalCompleted(goalId: Int)

    // Get the last 10 completed goals
    @Query("SELECT * FROM user_goals WHERE userId = :userId AND goalCompleted = 1 ORDER BY goalCompletedDate DESC LIMIT 10")
    suspend fun getLast10CompletedGoals(userId: Int): List<Goals>

    //get last added goal from a userId
    @Query("SELECT * FROM user_goals WHERE userId = :userId ORDER BY id DESC LIMIT 1")
    suspend fun getLastAddedGoal(userId: Int): Goals?
}
