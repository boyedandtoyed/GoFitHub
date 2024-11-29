package com.example.gofithub

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.database.UserActivity
import com.example.gofithub.database.UserActivityDao
import com.example.gofithub.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ActivityEnteringPage : AppCompatActivity() {

    private lateinit var userActivityDao: UserActivityDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entering_page)

        // Initialize DAO
        userActivityDao = AppDatabase.getInstance(this).userActivityDao()

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val etCaloriesBurned = findViewById<EditText>(R.id.etCaloriesBurned)
        val etAsanas = findViewById<EditText>(R.id.etPace)
        val etDistance = findViewById<EditText>(R.id.etDistance)
        val etTime = findViewById<EditText>(R.id.etTime)
        val etSpeed = findViewById<EditText>(R.id.etSpeed)
        val btnSubmitRunning = findViewById<Button>(R.id.btnSubmitRunning)

        // Retrieve data from intent
        val activityName = intent.getStringExtra("activityName") ?: "Unknown Activity"
        val userId = intent.getIntExtra("userId", -1)
        tvTitle.text = activityName

        btnSubmitRunning.setOnClickListener {
            val calories = etCaloriesBurned.text.toString().toIntOrNull()
            val pace = etAsanas.text.toString().toIntOrNull()
            val distance = etDistance.text.toString().toIntOrNull()
            val time = etTime.text.toString()
            val speed = etSpeed.text.toString().toIntOrNull()

            if (calories == null || pace == null || distance == null || speed == null || time.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields correctly!", Toast.LENGTH_SHORT).show()
            } else {
                val currentDate = getCurrentDate()
                saveActivityData(
                    userId, currentDate, activityName, calories, distance, time, speed
                )
            }
        }
    }

    private fun saveActivityData(
        userId: Int,
        activityStartDate: String,
        activityName: String,
        caloriesBurned: Int,
        distance: Int,
        time: String,
        speed: Int
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            val userActivity = UserActivity(
                userId = userId,
                activityStartDate = activityStartDate,
                activityCompletedDate = null,
                activityId = 0,
                activityName = activityName,
                activityCompleted = false,
                duration = time.toInt(),
                heartRate = 0,
                speed = speed,
                distance = distance,
                caloriesBurned = caloriesBurned
            )
            userActivityDao.insertUserActivity(userActivity)
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
