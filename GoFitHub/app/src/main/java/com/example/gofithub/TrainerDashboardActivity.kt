package com.example.gofithub

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.UserDashboardActivity
import database.AppDatabase
import kotlinx.coroutines.launch

class TrainerDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trainer_dashboard)

        val helloTextView = findViewById<TextView>(R.id.textViewHelloTrainer)
        val trainerDao = AppDatabase.getInstance(this).userDao()
        val id = intent.getIntExtra("trainerId", -1)

        lifecycleScope.launch {
            try {
                // Retrieve user from the database
                Log.d("Trainer ID coming in dashboard is----", id.toString())
                val trainer = trainerDao.getUserById(id)
                if (trainer != null) {
                    val name = trainer.firstName
                    helloTextView.text = "Hello, $name!"
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
