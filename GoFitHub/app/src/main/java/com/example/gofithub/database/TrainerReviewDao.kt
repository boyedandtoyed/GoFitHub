package com.example.gofithub.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrainerReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: TrainerReview)

    //get reviews for a trainer by trainerid
    @Query("SELECT * FROM trainer_reviews WHERE trainerUserId = :trainerId")
    suspend fun getReviewsForTrainer(trainerId: Int): List<TrainerReview>

    //get reviews given by certain userid
    @Query("SELECT * FROM trainer_reviews WHERE raterUserId = :userId")
    suspend fun getReviewsGivenByUser(userId: Int): List<TrainerReview>


}