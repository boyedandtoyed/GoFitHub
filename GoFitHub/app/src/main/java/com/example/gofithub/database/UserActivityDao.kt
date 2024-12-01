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
    @Query("SELECT * FROM user_activities WHERE userId = :userId ORDER BY activityCompletedDate DESC")
    suspend fun getAllUserActivitiesSortedByStartTime(userId: Int): List<UserActivity>



    //get last seven activities sorted by time
    @Query("SELECT * FROM user_activities WHERE userId = :userId ORDER BY activityCompletedDate DESC LIMIT 7")
    suspend fun getLastSevenCompletedUserActivitiesSortedByTime(userId: Int): List<UserActivity>

    @Query("SELECT * FROM user_activities WHERE userId = :userId AND activityCompletedDate >= :startDate ORDER BY activityCompletedDate DESC")
    suspend fun getLast10WeeksData(userId: Int, startDate: String): List<UserActivity>



}