package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TrainerRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_register)

        // Set up the experience level Spinner
        val experienceLevelSpinner = findViewById<Spinner>(R.id.experienceLevelSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.experience_levels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            experienceLevelSpinner.adapter = adapter
        }

        // Set up the specialty Spinner
        val specialtySpinner = findViewById<Spinner>(R.id.specialtySpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.specialties,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            specialtySpinner.adapter = adapter
        }

        // Set up the Terms and Conditions text view
        val termsTextView = findViewById<TextView>(R.id.termsText)
        termsTextView.setOnClickListener {
            val intent = Intent(this, TermsAndConditionsActivity::class.java)
            startActivity(intent)
        }
    }
}
