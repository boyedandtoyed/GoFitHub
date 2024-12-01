package com.example.gofithub

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.UserActivity
import com.example.gofithub.database.UserActivityDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentActivities : AppCompatActivity() {
    private lateinit var userActivityDao: UserActivityDao
    private lateinit var recentActivitiesLayout: LinearLayout
    private lateinit var lastSevenActivities: List<UserActivity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recent_activities)
        val userId = intent.getIntExtra("userId", -1)
        recentActivitiesLayout = findViewById<LinearLayout>(R.id.recentActivities)
        userActivityDao = AppDatabase.getInstance(this).userActivityDao()


        displayRecentActivities(userId)


    }

    private fun displayRecentActivities(userId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            lastSevenActivities = userActivityDao.getLastSevenCompletedUserActivitiesSortedByTime(userId)

            recentActivitiesLayout.removeAllViews()
            for (activity in lastSevenActivities) {
                val activityView = createActivityView(activity)
                recentActivitiesLayout.addView(activityView)
            }
        }
    }

    private fun createActivityView(activity: UserActivity): LinearLayout {
        //create linear layout for each activity with its details
        //create text views for each detail
        //details are activity name, date of completion, duration of activity, calories burned, distance if notnull, speed , average heart rate
        //all of them accessed directly from UserActivity

        val activityView = LinearLayout(this)
        activityView.orientation = LinearLayout.VERTICAL
        activityView.setPadding(16, 16, 16, 16)

        val activityNameView = TextView(this)
        activityNameView.text = "Activity Name: " + activity.activityName
        activityView.addView(activityNameView)

        val activityDateView = TextView(this)
        activityDateView.text = "Completed Date: " + activity.activityCompletedDate
        activityView.addView(activityDateView)

        val activityDurationView = TextView(this)
        activityDurationView.text = "Duration: " + activity.duration.toString()
        activityView.addView(activityDurationView)

        val activityCaloriesView = TextView(this)
        activityCaloriesView.text = "Calories Burned: " + activity.caloriesBurned.toString()
        activityView.addView(activityCaloriesView)

        if (activity.distance != null) {
            val activityDistanceView = TextView(this)
            activityDistanceView.text = "Distance Travelled: " + activity.distance.toString()
            activityView.addView(activityDistanceView)
        }

        if (activity.speed != null) {
            val activitySpeedView = TextView(this)
            activitySpeedView.text = "Average Speed: " + activity.speed.toString()
            activityView.addView(activitySpeedView)
        }

        val activityHeartRateView = TextView(this)
        activityHeartRateView.text = "Average Heart Rate: " + activity.heartRate.toString()
        activityView.addView(activityHeartRateView)

        return activityView

    }
}