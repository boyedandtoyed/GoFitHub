package com.example.gofithub.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopUser(topUser: TopUser): Long

    //get top user
    @Query("SELECT * FROM top_users ORDER BY goalCount DESC LIMIT 1")
    suspend fun getTopUserByGoalCount(): TopUser?


}