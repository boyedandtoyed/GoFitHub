package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class Activities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_activities)

        val newActivityButton = findViewById<Button>(R.id.newActivity)
        newActivityButton.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
        }

        val recentActivityButton = findViewById<Button>(R.id.recentActivity)
        recentActivityButton.setOnClickListener {
            val intent = Intent(this, RecentActivity::class.java)
            startActivity(intent)
        }

        val goalsButton = findViewById<Button>(R.id.goals)
        goalsButton.setOnClickListener {
            val intent = Intent(this, Goals::class.java)
            startActivity(intent)
        }

        val myProgressButton = findViewById<Button>(R.id.myProgress)
        myProgressButton.setOnClickListener {
            val intent = Intent(this, MyProgress::class.java)
            startActivity(intent)
        }
    }
}