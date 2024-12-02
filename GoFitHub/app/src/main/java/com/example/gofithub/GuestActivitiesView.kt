package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.ViewActivityPage
import com.example.gofithub.database.ActivitiesDao
import com.example.gofithub.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GuestActivitiesView : AppCompatActivity() {

    private lateinit var predefinedActivitiesLayout: LinearLayout
    private lateinit var activitiesDao: ActivitiesDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guest_activities_view)


        activitiesDao = AppDatabase.getInstance(this).activitiesDao()
        predefinedActivitiesLayout = findViewById(R.id.allActivitiesLayout)
        fetchActivities()
    }

    private fun fetchActivities() {
        lifecycleScope.launch {
            // Fetch predefined activities (where userId = -1)
            val predefinedActivities = withContext(Dispatchers.IO) {
                activitiesDao.getActivitiesByUserId(-1)  // Fetch activities with userId
            }


            // Display predefined activities
            predefinedActivitiesLayout.removeAllViews()
            for (activity in predefinedActivities) {
                if (activity.userId == -1) {
                    val button = createActivityButton(activity.activityName)
                    predefinedActivitiesLayout.addView(button)
                }
            }


        }
    }

    // Function to create a button for an activity
    private fun createActivityButton(activityName: String): Button {
        return Button(this).apply {
            text = activityName


            // Set layout parameters
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            ).apply {
                setMargins(0, 10, 0, 10)  // Set margin between buttons
            }

            // Dynamically set the button color and text color
            setBackgroundColor(ContextCompat.getColor(context, R.color.button_color))
            setTextColor(ContextCompat.getColor(context, R.color.button_text_color))


        }
    }
}