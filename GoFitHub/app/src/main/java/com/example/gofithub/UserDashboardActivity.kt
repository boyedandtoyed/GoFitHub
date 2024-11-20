package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.UserRegisterActivity
import com.example.gofithub.database.User
import database.AppDatabase
import kotlinx.coroutines.launch

class UserDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_dashboard)

        val helloTextView = findViewById<TextView>(R.id.textViewHello)
        val userDao = AppDatabase.getInstance(this).userDao()
        val id = intent.getIntExtra("userId", -1)

        lifecycleScope.launch {
            try {
                // Retrieve user from the database
                val user = userDao.getUserById(id)
                if (user != null) {
                    val name = user.firstName
                    helloTextView.text = "Hello, $name!"
                } else {
                    helloTextView.text = "Unknown User"
                }
            } catch (e: Exception) {
                Log.e("UserDashboardActivity", "Error retrieving user", e)
                Toast.makeText(this@UserDashboardActivity, "Error retrieving user", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
