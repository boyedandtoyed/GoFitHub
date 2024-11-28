package database

import android.content.Context
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
import com.example.gofithub.database.Actvities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [User::class, Trainer::class, UserActivity::class, Actvities::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun trainerDao(): TrainerDao
    abstract fun userActivityDao(): UserActivityDao
    abstract fun activitiesDao(): ActivitiesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.activitiesDao())
                    }
                }
            }

            suspend fun populateDatabase(dao: ActivitiesDao) {
                // Insert six default activities
                dao.insertActivity(null, activityName = "Running")
                dao.insertActivity(null, activityName = "Walking")
                dao.insertActivity(null, activityName = "Cycling")
                dao.insertActivity(null, activityName = "Yoga")
                dao.insertActivity(null, activityName = "High-Intensity Interval Training (HIIT)")
                dao.insertActivity(null, activityName = "Weightlifting")
            }
        }
    }
}
