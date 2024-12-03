package com.example.gofithub

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.TopUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Friends : AppCompatActivity() {

    private lateinit var topGoalsLayout: LinearLayout
    private lateinit var topCaloriesLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        topGoalsLayout = findViewById(R.id.topGoalsLayout)
        topCaloriesLayout = findViewById(R.id.topCaloriesLayout)

        loadTopUsers()
    }

    private fun loadTopUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val goalsDao = AppDatabase.getInstance(this@Friends).goalsDao()
            val topGoalsUsers = goalsDao.getTopUsersByCompletedGoals()
            val topCaloriesUsers = goalsDao.getTopUsersByCaloriesBurned()

            withContext(Dispatchers.Main) {
                displayTopUsers(topGoalsUsers, topGoalsLayout, "Completed Goals")
                displayTopUsers(topCaloriesUsers, topCaloriesLayout, "Calories Burned")
            }
        }
    }

    private fun displayTopUsers(users: List<TopUser>, layout: LinearLayout, metric: String) {
        for (user in users) {
            val textView = TextView(this)
            textView.text = "User ID: ${user.userId} - $metric: ${if (metric == "Completed Goals") user.goalCount else user.totalCalories}"
            textView.textSize = 16f
            layout.addView(textView)
        }
    }
}
