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

    // Get top 5 users by completed goals
    @Query(" SELECT userId, COUNT(*) AS goalCount FROM user_goals WHERE goalCompleted = 1 GROUP BY userId ORDER BY goalCount DESC LIMIT 5")
    suspend fun getTopUsersByCompletedGoals(): List<TopUser>

    // Get top 5 users by calories burned (sum of caloriesTarget)
    @Query("SELECT userId, SUM(caloriesTarget) AS totalCalories FROM user_goals WHERE goalCompleted = 1 GROUP BY userId ORDER BY totalCalories DESC LIMIT 5")
    suspend fun getTopUsersByCaloriesBurned(): List<TopUser>
}
