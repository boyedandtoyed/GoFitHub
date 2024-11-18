package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import database.AppDatabase
import com.example.gofithub.database.UserDao
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
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the email exists in the database
            lifecycleScope.launch {
                val userDao: UserDao = AppDatabase.getInstance(applicationContext).userDao()
                val user = userDao.getUserByEmail(email)

                if (user == null) {
                    // If the email doesn't exist
                    Toast.makeText(this@LoginActivity, "Email not registered.", Toast.LENGTH_SHORT).show()
                } else {
                    // If email exists, verify the password
                    val hashedPassword = user.password
                    if (SecurityUtils.verifyPassword(password, hashedPassword)) {
                        // If passwords match, go to UserDashboardPage
                        val intent = Intent(this@LoginActivity, UserDashboardActivity::class.java)
                        startActivity(intent)
                        finish() // Close the LoginActivity so the user can't go back
                    } else {
                        // If passwords don't match
                        Toast.makeText(this@LoginActivity, "Incorrect password.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun openRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
