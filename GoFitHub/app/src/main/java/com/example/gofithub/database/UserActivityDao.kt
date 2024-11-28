package com.example.gofithub.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserActivity(userActivity: UserActivity): Long

    //return all user activities sorted by start time
    @Query("SELECT * FROM user_activities WHERE userId = :userId ORDER BY activityStartDate DESC")
    suspend fun getAllUserActivitiesSortedByStartTime(userId: Int): List<UserActivity>

    //get last seven completed activities sorted by time
    @Query("SELECT * FROM user_activities WHERE userId = :userId AND activityCompleted = 1 ORDER BY activityStartDate DESC LIMIT 7")
    suspend fun getLastSevenCompletedUserActivitiesSortedByTime(userId: Int): List<UserActivity>

    //set activity completed
    @Query("UPDATE user_activities SET activityCompleted = :activityCompleted WHERE id = :activityId")
    suspend fun setActivityCompleted(activityId: Int, activityCompleted: Boolean)




}