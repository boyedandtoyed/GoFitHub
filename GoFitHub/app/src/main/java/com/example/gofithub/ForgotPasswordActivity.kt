package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.utils.SecurityUtils
import com.example.gofithub.database.AppDatabase
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        val etForgotEmail = findViewById<EditText>(R.id.etForgotEmail)
        val btnResetPassword = findViewById<Button>(R.id.btnResetPassword)
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnBackToLogin = findViewById<Button>(R.id.btnBackToLogin)

        btnBackToLogin.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnResetPassword.setOnClickListener {
            val email = etForgotEmail.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simulate checking email in the database
            lifecycleScope.launch {
                val appDatabase = AppDatabase.getInstance(applicationContext)
                val userDao = appDatabase.userDao()
                val trainerDao = appDatabase.trainerDao()

                val user = userDao.getUserByEmail(email) // Use your UserDao
                val trainer = trainerDao.getTrainerByEmail(email) // Use your TrainerDao

                if ((user != null) || (trainer != null)) {
                    etForgotEmail.isEnabled = false
                    Toast.makeText(this@ForgotPasswordActivity, "Email exists.", Toast.LENGTH_SHORT)
                        .show()

                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Email link sent successfully.",
                        Toast.LENGTH_SHORT
                    ).show()

                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Now reset your password.",
                        Toast.LENGTH_SHORT
                    ).show()

                    etNewPassword.isEnabled = true
                    etNewPassword.setBackgroundColor(ContextCompat.getColor(this@ForgotPasswordActivity, R.color.white))
                    etConfirmPassword.isEnabled = true
                    etConfirmPassword.setBackgroundColor(ContextCompat.getColor(this@ForgotPasswordActivity, R.color.white))

                    btnResetPassword.setOnClickListener {
                        val validated = validate_password(etNewPassword.text.toString(), etConfirmPassword.text.toString())
                        if (validated){
                            if (user != null) {
                               lifecycleScope.launch {
                                   val hashedPassword = SecurityUtils.hashPassword(etNewPassword.text.toString())
                                   userDao.updatePassword(email, hashedPassword)
                               }
                                Toast.makeText(this@ForgotPasswordActivity, "Password updated successfully.", Toast.LENGTH_SHORT).show()
                                intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()

                            }
                            else if (trainer != null) {
                                lifecycleScope.launch {
                                    val hashedPassword = SecurityUtils.hashPassword(etNewPassword.text.toString())
                                    trainerDao.updatePassword(email, hashedPassword)
                                }
                                Toast.makeText(this@ForgotPasswordActivity, "Password updated successfully.", Toast.LENGTH_SHORT).show()
                                intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                        }
                        else{
                            Toast.makeText(this@ForgotPasswordActivity, "Password update failed. Please enter passwords correctly.", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        }

                    }

                }

                }
            }


    private fun validate_password(password: String, confirmPassword: String): Boolean {
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show()
            return false

        }
        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please confirm your password.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            return false

        }
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
        val isValid = password.matches(passwordRegex.toRegex())
        if (!isValid) {
            Toast.makeText(
                this,
                "Password must be at least 10 characters long and include at least one character and alphanumeric character.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return isValid
    }
}