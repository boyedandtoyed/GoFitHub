package com.example.gofithub.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gofithub.database.Activity

@Dao
interface ActivitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity): Long

    @Query("SELECT * FROM activities WHERE activityName = :activityName")
    suspend fun getActivityByName(activityName: String): Activity?

    //get all activities except userId
    @Query("SELECT * FROM activities WHERE userId != :userId")
    suspend fun getActivities(userId: Int): List<Activity>

    //get all activities where userId equals to passed userId
    @Query("SELECT * FROM activities WHERE userId = :userId")
    suspend fun getActivitiesByUserId(userId: Int): List<Activity>


}