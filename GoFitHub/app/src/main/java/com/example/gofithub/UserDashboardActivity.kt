package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gofithub.UserRegisterActivity
import com.example.gofithub.database.User
import database.AppDatabase

class UserDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_dashboard)

        val helloTextView = findViewById<TextView>(R.id.textViewHello)
        val userDao = AppDatabase.getInstance(this).userDao()
        val idString = (intent.getStringExtra("userId"))?.trim()

        // Safely convert string to Int and handle possible errors
        val userId = try {
            idString?.toInt() ?: 1  // Default to 1 if null or invalid
        } catch (e: NumberFormatException) {
            // If the ID can't be converted to Int, show an error or use default value
            Toast.makeText(this, "Invalid user ID format. Something is wrong with your unique ID", Toast.LENGTH_SHORT).show()
            intent = Intent(this@UserDashboardActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Retrieve user from database
        val user = userDao.getUserById(userId as Int)
        if (user != null) {
            val name = user.firstName
            helloTextView.text = "Hello, $name!"
        } else {
            helloTextView.text = "Unkown User"
        }
    }
}
