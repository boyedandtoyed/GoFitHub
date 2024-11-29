package com.example.gofithub

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gofithub.database.AppDatabase

class GuestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guest)
        val adImage = findViewById<ImageView>(R.id.adImage)
        val adPanel = findViewById<LinearLayout>(R.id.adPanel)
        val helloTextView = findViewById<TextView>(R.id.textViewHello)
        val activitiesButton = findViewById<Button>(R.id.activitiesButton)
        val friendsButton = findViewById<Button>(R.id.friendsButton)
        val trainingButton = findViewById<Button>(R.id.trainingButton)
        val plansButton = findViewById<Button>(R.id.plansButton)

        friendsButton.visibility=View.GONE;
        trainingButton.visibility=View.GONE;
        activitiesButton.visibility=View.GONE;



    }
}
