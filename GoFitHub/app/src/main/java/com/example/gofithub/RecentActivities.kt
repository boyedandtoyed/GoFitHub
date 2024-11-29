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
    private lateinit var userActivities: List<UserActivity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recent_activities)
        val userId = intent.getIntExtra("userId", -1)
        recentActivitiesLayout = findViewById<LinearLayout>(R.id.recentActivities)
        userActivityDao = AppDatabase.getInstance(this).userActivityDao()

        fetchRecentActivities(userId)
        displayRecentActivities(lastSevenActivities, recentActivitiesLayout)


    }
    private fun fetchRecentActivities(userId: Int) {

        lifecycleScope.launch(Dispatchers.IO) {
            lastSevenActivities = userActivityDao.getLastSevenCompletedUserActivitiesSortedByTime(userId)
        }

    }
    private fun displayRecentActivities(lastSevenActivities: List<UserActivity>, recentActivitiesLayout: LinearLayout) {
        recentActivitiesLayout.removeAllViews()
        for (activity in lastSevenActivities) {
            val activityView = createActivityView(activity)
            recentActivitiesLayout.addView(activityView)

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
        activityNameView.text = activity.activityName
        activityView.addView(activityNameView)

        val activityDateView = TextView(this)
        activityDateView.text = activity.activityCompletedDate
        activityView.addView(activityDateView)

        val activityDurationView = TextView(this)
        activityDurationView.text = activity.duration.toString()
        activityView.addView(activityDurationView)

        val activityCaloriesView = TextView(this)
        activityCaloriesView.text = activity.caloriesBurned.toString()
        activityView.addView(activityCaloriesView)

        if (activity.distance != null) {
            val activityDistanceView = TextView(this)
            activityDistanceView.text = activity.distance.toString()
            activityView.addView(activityDistanceView)
        }

        if (activity.speed != null) {
            val activitySpeedView = TextView(this)
            activitySpeedView.text = activity.speed.toString()
            activityView.addView(activitySpeedView)
        }

        val activityHeartRateView = TextView(this)
        activityHeartRateView.text = activity.heartRate.toString()
        activityView.addView(activityHeartRateView)

        return activityView

    }
}