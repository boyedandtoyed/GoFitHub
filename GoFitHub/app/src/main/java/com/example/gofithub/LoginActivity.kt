package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.UserDao
import com.example.gofithub.database.TrainerDao // Import TrainerDao
import com.example.gofithub.utils.SecurityUtils
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)

        // Open RegisterActivity if register button is clicked
        findViewById<Button>(R.id.loginRegisterButton).setOnClickListener {
            openRegisterActivity()
        }
        forgotPasswordTextView.setOnClickListener {
            // Handle forgot password functionality here
        }

        // Login Button Click Listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Check if the email exists in the database
            loginUser(email, password)
        }
    }

    private fun openRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch {
            val appDatabase = AppDatabase.getInstance(applicationContext)
            val userDao = appDatabase.userDao()
            val trainerDao = appDatabase.trainerDao()

            // Check user credentials
            val user = userDao.getUserByEmail(email)
            val trainer = trainerDao.getTrainerByEmail(email)

            if (user != null) {
                if (SecurityUtils.verifyPassword(password, user.password)) {
                    // User authenticated, navigate to UserDashboardActivity
                    val intent = Intent(this@LoginActivity, UserDashboardActivity::class.java)
                    // Pass necessary data (e.g., user ID) to the dashboard activity
                    intent.putExtra("userId", user.id)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Incorrect password.", Toast.LENGTH_SHORT)
                        .show()
                    return@launch
                }
            } else if (trainer != null) {
                if (SecurityUtils.verifyPassword(password, trainer.password)) {
                    // Trainer authenticated, navigate to TrainerDashboardActivity
                    val intent = Intent(this@LoginActivity, TrainerDashboardActivity::class.java)
                    // Pass necessary data (e.g., trainer ID) to the dashboard activity
                    intent.putExtra("trainerId", trainer.id)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Incorrect password.", Toast.LENGTH_SHORT)
                        .show()
                    return@launch
                }
            } else {
                Toast.makeText(this@LoginActivity, "Email not found.", Toast.LENGTH_SHORT).show()
                return@launch
            }
        }

    }
}
