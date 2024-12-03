package com.example.gofithub.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gofithub.database.ActivitiesDao
import com.example.gofithub.database.Trainer
import com.example.gofithub.database.TrainerDao
import com.example.gofithub.database.User
import com.example.gofithub.database.UserActivity
import com.example.gofithub.database.UserDao
import com.example.gofithub.database.UserActivityDao
import com.example.gofithub.database.Activity
import com.example.gofithub.database.Goals
import com.example.gofithub.database.GoalsDao
import com.example.gofithub.database.TrainerReviewDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Database(entities = [User::class, Trainer::class, UserActivity::class, Activity::class, Goals::class, TrainerReview::class, TrainerTraineeRelation::class, CreateWorkout::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun trainerDao(): TrainerDao
    abstract fun userActivityDao(): UserActivityDao
    abstract fun activitiesDao(): ActivitiesDao
    abstract fun goalsDao(): GoalsDao
    abstract fun trainerReviewDao(): TrainerReviewDao
    abstract fun trainerTraineeRelationDao(): TrainerTraineeRelationDao
    abstract fun createWorkoutDao(): CreateWorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDatabaseCallback(context.applicationContext)) // Add callback to insert default activities
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val activitiesDao = AppDatabase.getInstance(context).activitiesDao()


            // Insert predefined activities in a background thread
            CoroutineScope(Dispatchers.IO).launch {
                populateDatabase(activitiesDao)

            }
        }

        private suspend fun populateDatabase(dao: ActivitiesDao) {
            // Insert six default activities into the database
            dao.insertActivity(Activity(activityName = "Running", userId = -1))
            dao.insertActivity(Activity(activityName = "Walking", userId = -1))
            dao.insertActivity(Activity(activityName = "Cycling", userId = -1))
            dao.insertActivity(Activity(activityName = "Yoga", userId = -1))
            dao.insertActivity(Activity(activityName = "High-Intensity Interval Training (HIIT)", userId = -1))
            dao.insertActivity(Activity(activityName = "Weightlifting", userId = -1))
            Log.d("AppDatabaseCallback---", "Predefined activities inserted")
        }

    }
}
