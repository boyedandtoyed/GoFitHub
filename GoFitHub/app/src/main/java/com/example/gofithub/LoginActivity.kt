package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import database.AppDatabase
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

        // Open RegisterActivity if register button is clicked
        findViewById<Button>(R.id.loginRegisterButton).setOnClickListener {
            openRegisterActivity()
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
            lifecycleScope.launch {
                val appDatabase = AppDatabase.getInstance(applicationContext)
                val userDao: UserDao = appDatabase.userDao()
                val trainerDao: TrainerDao = appDatabase.trainerDao()

                // Check in the User table
                val user = userDao.getUserByEmail(email)
                if (user != null) {
                    // User exists, verify password
                    val hashedPassword = user.password
                    if (SecurityUtils.verifyPassword(password, hashedPassword)) {
                        // Password matches, go to UserDashboardActivity
                        val intent = Intent(this@LoginActivity, UserDashboardActivity::class.java)
                        intent.putExtra("userId", user.id)
                        startActivity(intent)
                        finish()
                    } else {
                        // Password doesn't match
                        Toast.makeText(
                            this@LoginActivity,
                            "Incorrect password.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                // Check in the Trainer table
                val trainer = trainerDao.getTrainerByEmail(email)
                if (trainer != null) {
                    // Trainer exists, verify password
                    val hashedPassword = trainer.password
                    if (SecurityUtils.verifyPassword(password, hashedPassword)) {
                        // Password matches, go to TrainerDashboardActivity
                        val intent = Intent(this@LoginActivity, TrainerDashboardActivity::class.java)
                        intent.putExtra("trainerId", trainer.id)
                        startActivity(intent)
                        finish()
                    } else {
                        // Password doesn't match
                        Toast.makeText(
                            this@LoginActivity,
                            "Incorrect password.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                // If email is not found in both tables
                Toast.makeText(
                    this@LoginActivity,
                    "Email not registered.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun openRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
