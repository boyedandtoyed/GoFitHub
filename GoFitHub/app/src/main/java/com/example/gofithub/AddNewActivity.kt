package com.example.gofithub

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddNewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new)

        val buttonCycling = findViewById<Button>(R.id.btnCycling)
        val buttonWeightlifting = findViewById<Button>(R.id.btnWeightlifting)
        val buttonRunning = findViewById<Button>(R.id.btnRunning)
        val buttonHIIT = findViewById<Button>(R.id.btnHIIT)
        val buttonYoga = findViewById<Button>(R.id.btnYoga)

        buttonCycling.setOnClickListener {
            // Handle button click for Cycling
        }

        buttonWeightlifting.setOnClickListener {
            // Handle button click for Weightlifting
        }

        buttonRunning.setOnClickListener {
            // Handle button click for Running
        }

        buttonHIIT.setOnClickListener {
            // Handle button click for HIIT
        }

        buttonYoga.setOnClickListener {
            // Handle button click for Yoga
        }



    }
}