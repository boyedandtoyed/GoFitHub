package com.example.gofithub

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.GoalsDao
import com.example.gofithub.database.TrainerTraineeRelationDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckRoster : AppCompatActivity() {

    private lateinit var traineeListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_roster)

        traineeListLayout = findViewById(R.id.traineeListLayout)

        val trainerId = intent.getIntExtra("trainerId", -1)

        loadTraineesProgress(trainerId)
    }

    private fun loadTraineesProgress(trainerId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val trainerTraineeRelationDao = AppDatabase.getInstance(this@CheckRoster).trainerTraineeRelationDao()
            val goalsDao = AppDatabase.getInstance(this@CheckRoster).goalsDao()

            // Get the list of trainees for the current trainer
            val trainees = trainerTraineeRelationDao.getTraineesForTrainer(trainerId)

            withContext(Dispatchers.Main) {
                // Loop through each trainee and fetch their progress
                for (trainee in trainees) {
                    val traineeId = trainee.traineeId

                    // Fetch completed goals and calories burned for this trainee
                    val completedGoals = goalsDao.getIncompleteGoals(traineeId).size
                    val totalCalories = goalsDao.getLast10CompletedGoals(traineeId).sumBy { it.caloriesTarget }

                    // Create a TextView for displaying the trainee's progress
                    val traineeInfoTextView = TextView(this@CheckRoster)
                    traineeInfoTextView.text = "Trainee ID: ${trainee.traineeId}, Goals Completed: $completedGoals, Calories Burned: $totalCalories"
                    traineeInfoTextView.textSize = 16f

                    // Add the TextView to the layout
                    traineeListLayout.addView(traineeInfoTextView)
                }
            }
        }
    }
}
