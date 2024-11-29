package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.Activity
import com.example.gofithub.database.ActivitiesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat

class ViewActivityPage : AppCompatActivity() {

    private lateinit var predefinedActivitiesLayout: LinearLayout
    private lateinit var userActivitiesLayout: LinearLayout
    private lateinit var addActivityButton: Button
    private lateinit var newActivityEditText: EditText
    private lateinit var activitiesDao: ActivitiesDao
    private var userId: Int = -1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_page)

        // Retrieve the user ID from the intent
        userId = intent.getIntExtra("userId", -1)
        Log.d("ViewActivityPage--", "User ID: $userId")

        // Initialize views
        predefinedActivitiesLayout = findViewById(R.id.preDefinedActivities)
        userActivitiesLayout = findViewById(R.id.currentUserAddedActivities)
        addActivityButton = findViewById(R.id.addActivities)
        newActivityEditText = findViewById(R.id.newActivityEditText)

        // Initialize DAO
        activitiesDao = AppDatabase.getInstance(this).activitiesDao()

        // Fetch predefined activities and user activities
        fetchActivities()

        // Handle the "Add New Activity" button
        addActivityButton.setOnClickListener {
            val newActivityName = newActivityEditText.text.toString()
            if (newActivityName.isNotEmpty()) {
                val newActivity = Activity(activityName = newActivityName, userId = userId)
                addUserActivityToUI(newActivity)  // Add the new activity to the UI
                saveActivity(newActivity)  // Save the new activity to the database
                newActivityEditText.text.clear()  // Clear the input field
            }
            else{
                Toast.makeText(this, "Please enter an activity name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to fetch activities from database and display them
    private fun fetchActivities() {
        lifecycleScope.launch {
            // Fetch predefined activities (where userId = -1)
            val predefinedActivities = withContext(Dispatchers.IO) {
                activitiesDao.getActivities()  // Fetch activities with userId = -1
            }

            // Fetch user-specific activities
            val userActivities = withContext(Dispatchers.IO) {
                activitiesDao.getActivitiesByUserId(userId)  // Fetch user-specific activities
            }

            // Display predefined activities
            predefinedActivitiesLayout.removeAllViews()
            for (activity in predefinedActivities) {
                    val button = createActivityButton(activity.activityName)
                    predefinedActivitiesLayout.addView(button)
            }

            // Display user-defined activities
            userActivitiesLayout.removeAllViews()
            for (userActivity in userActivities) {
                val button = createActivityButton(userActivity.activityName)
                userActivitiesLayout.addView(button)
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


    // Function to save activity to the database
    private fun saveActivity(activity: Activity) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                activitiesDao.insertActivity(activity)
            }
        }
    }

    // Function to add user activity to the UI dynamically
    private fun addUserActivityToUI(activity: Activity) {
        val button = createActivityButton(activity.activityName)
        userActivitiesLayout.addView(button)
    }
}
