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
import android.content.Intent
import com.example.gofithub.R
import com.example.gofithub.TraineesViewPage

class TrainerDashboardActivity : AppCompatActivity() {

    private lateinit var helloTextView: TextView
    private lateinit var traineesButton: Button
    private lateinit var createPlansButton: Button
    private lateinit var checkRosterButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trainer_dashboard)

        helloTextView = findViewById(R.id.textViewHelloTrainer)
        traineesButton = findViewById(R.id.traineesListButton)
        createPlansButton = findViewById(R.id.createWorkoutPlansButton)
        checkRosterButton = findViewById(R.id.checkRosterButton)


        val trainerDao = AppDatabase.getInstance(this).trainerDao()
        val id = intent.getIntExtra("trainerId", -1)
        Log.d("Trainer ID is----", id.toString())

        lifecycleScope.launch {
            try {

                val trainer = trainerDao.getTrainerById(id)
                if (trainer != null) {
                    Log.d("Trainer Name is----", trainer.toString())
                    val name = trainer.firstName
                    Log.d("Trainer Name is----", name)
                    helloTextView.text = "Hello, $name!"

                    //set onlick listeners for buttons
                    traineesButton.setOnClickListener {
                        // Handle trainees button click
                        val intent = Intent(this@TrainerDashboardActivity, TraineesViewPage::class.java)
                        intent.putExtra("trainerId", id)
                        startActivity(intent)

                        Toast.makeText(this@TrainerDashboardActivity, "Trainees button clicked", Toast.LENGTH_SHORT).show()
                    }

                    createPlansButton.setOnClickListener {
                        // Handle create plans button click
                        Toast.makeText(this@TrainerDashboardActivity, "Create plans button clicked", Toast.LENGTH_SHORT).show()
                    }

                    checkRosterButton.setOnClickListener {
                        // Handle check roster button click
                        Toast.makeText(this@TrainerDashboardActivity, "Check roster button clicked", Toast.LENGTH_SHORT).show()
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
