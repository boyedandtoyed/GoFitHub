package com.example.gofithub.database

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM user_info WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM user_info WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM user_info")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT COUNT(*) FROM user_info WHERE email = :email")
    suspend fun isEmailExists(email: String): Int

    @Query("UPDATE user_info SET isSubscribed = :isSubscribed WHERE id = :userId")
    suspend fun setSubscribedStatus(userId: Int, isSubscribed: Boolean)

    @Query("UPDATE user_info SET password = :newPassword WHERE email = :email")
    suspend fun updatePassword(email: String, newPassword: String)

}

