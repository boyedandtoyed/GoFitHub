package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gofithub.ProgressReportView
import com.example.gofithub.GoalsViewPage


class Activities : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_activities)
        val userId = intent.getIntExtra("userId", -1)

        val newActivityButton = findViewById<Button>(R.id.viewActivity)
        newActivityButton.setOnClickListener {
            val intent = Intent(this, ViewActivityPage::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        val recentActivityButton = findViewById<Button>(R.id.recentActivity)
        recentActivityButton.setOnClickListener {
            val intent = Intent(this, RecentActivities::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        val goalsButton = findViewById<Button>(R.id.goals)
        goalsButton.setOnClickListener {
            val intent = Intent(this, GoalsViewPage::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        val myProgressButton = findViewById<Button>(R.id.myProgress)
        myProgressButton.setOnClickListener {
            val intent = Intent(this, ProgressReportView::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}