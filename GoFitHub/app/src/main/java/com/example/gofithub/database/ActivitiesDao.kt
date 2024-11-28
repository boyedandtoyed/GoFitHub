package com.example.gofithub.database

import android.app.Activity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ActivitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(userid: Int?, activityName: String): Long
}