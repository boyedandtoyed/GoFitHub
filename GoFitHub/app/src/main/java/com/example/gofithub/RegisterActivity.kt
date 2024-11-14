package com.example.gofithub

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Set up button click listeners
        val trainerButton: Button = findViewById(R.id.buttonTrainerRegister)
        val generalUserButton: Button = findViewById(R.id.buttonGeneralUserRegister)

        trainerButton.setOnClickListener {
            Toast.makeText(this, "Trainer selected", Toast.LENGTH_SHORT).show()
            // Navigate to trainer registration page if needed
        }

        generalUserButton.setOnClickListener {
            Toast.makeText(this, "General User selected", Toast.LENGTH_SHORT).show()
            // Navigate to general user registration page if needed
        }
    }
}
