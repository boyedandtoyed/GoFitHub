package com.example.gofithub

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.UserDashboardActivity
import com.example.gofithub.database.AppDatabase
import kotlinx.coroutines.launch

class TrainerDashboardActivity : AppCompatActivity() {

    private lateinit var helloTextView: TextView
    private lateinit var traineesButton: Button
    private lateinit var createPlansButton: Button
    private lateinit var manageSessionsButton: Button
    private lateinit var checkRosterButton: Button
    private lateinit var homeButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trainer_dashboard)

        helloTextView = findViewById(R.id.textViewHelloTrainer)
        traineesButton = findViewById(R.id.traineesListButton)
        createPlansButton = findViewById(R.id.createWorkoutPlansButton)
        manageSessionsButton = findViewById(R.id.manageSessionsButton)
        checkRosterButton = findViewById(R.id.checkRosterButton)
        homeButton = findViewById(R.id.homeButton)


        val trainerDao = AppDatabase.getInstance(this).trainerDao()
        val id = intent.getIntExtra("trainerId", -1)

        lifecycleScope.launch {
            try {
                // Retrieve user from the database
                Log.d("Trainer ID coming in dashboard is----", id.toString())
                val trainer = trainerDao.getTrainerById(id)
                if (trainer != null) {
                    val name = trainer.firstName
                    Log.d("Trainer Name is----", name)
                    helloTextView.text = "Hello, $name!"

                    //set onlick listeners for buttons
                    traineesButton.setOnClickListener {
                        // Handle trainees button click
                        Toast.makeText(this@TrainerDashboardActivity, "Trainees button clicked", Toast.LENGTH_SHORT).show()
                    }

                    createPlansButton.setOnClickListener {
                        // Handle create plans button click
                        Toast.makeText(this@TrainerDashboardActivity, "Create plans button clicked", Toast.LENGTH_SHORT).show()
                    }

                    manageSessionsButton.setOnClickListener {
                        // Handle manage sessions button click
                        Toast.makeText(this@TrainerDashboardActivity, "Manage sessions button clicked", Toast.LENGTH_SHORT).show()
                    }

                    checkRosterButton.setOnClickListener {
                        // Handle check roster button click
                        Toast.makeText(this@TrainerDashboardActivity, "Check roster button clicked", Toast.LENGTH_SHORT).show()
                    }

                    homeButton.setOnClickListener {
                        // Handle home button click
                        Toast.makeText(this@TrainerDashboardActivity, "Home button clicked", Toast.LENGTH_SHORT).show()
                    }


                } else {
                    helloTextView.text = "Unknown Trainer"
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@TrainerDashboardActivity,
                    "Error retrieving trainer",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}
