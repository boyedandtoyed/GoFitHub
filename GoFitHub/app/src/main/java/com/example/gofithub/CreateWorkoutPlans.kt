package com.example.gofithub

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.CreateWorkout
import kotlinx.coroutines.launch

class CreateWorkoutPlans : AppCompatActivity() {
    private var workoutDao: com.example.gofithub.database.CreateWorkoutDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Optional for immersive edge-to-edge layout
        setContentView(R.layout.activity_create_workout_plans)

        workoutDao = AppDatabase.getInstance(this).createWorkoutDao()
        val trainerId = intent.getIntExtra("trainerId", -1)

        // Initialize UI components
        val youtubeLinkEditText: EditText = findViewById(R.id.youtubeLinkEditText)
        val submitButton: Button = findViewById(R.id.submitButton)

        // Set up the button click listener to handle the entered link
        submitButton.setOnClickListener {
            val youtubeLink = youtubeLinkEditText.text.toString()

            // Validate if the entered link is not empty
            if (youtubeLink.isNotEmpty()) {
               saveLinkToDatabase(youtubeLink, trainerId)

                Toast.makeText(this, "YouTube link added: $youtubeLink", Toast.LENGTH_SHORT).show()

                // Clear the EditText field after submission
                youtubeLinkEditText.text.clear()
            } else {
                // Show a toast if the link is empty
                Toast.makeText(this, "Please enter a valid YouTube link.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // This function will be used later to save the data in the database
    private fun saveLinkToDatabase(youtubeLink: String , trainerId: Int) {
        //add link to database
        lifecycleScope.launch {
            val workout = CreateWorkout(youtubeLink = youtubeLink, trainerId = trainerId)
            workoutDao?.insertWorkout(workout)
        }
    }
}
