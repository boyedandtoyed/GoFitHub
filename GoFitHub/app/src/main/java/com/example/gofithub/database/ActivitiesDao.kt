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

    //get all activities where userId is -1
    @Query("SELECT * FROM activities WHERE userId = -1")
    suspend fun getActivities(): List<Activity>

    //get all activities where userId equals to passed userId
    @Query("SELECT * FROM activities WHERE userId = :userId")
    suspend fun getActivitiesByUserId(userId: Int): List<Activity>


}