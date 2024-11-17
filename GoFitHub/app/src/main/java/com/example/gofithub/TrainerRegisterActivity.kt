package com.example.gofithub

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText


class TrainerRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainer_register)

        // Set up the experience level Spinner
        val experienceLevelSpinner = findViewById<Spinner>(R.id.experienceLevelSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.experience_levels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            experienceLevelSpinner.adapter = adapter
        }

        // Set up the specialty Spinner
        val specialtySpinner = findViewById<Spinner>(R.id.specialtySpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.specialties,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            specialtySpinner.adapter = adapter
        }

        // Set up the Terms and Conditions text view
        val termsTextView = findViewById<TextView>(R.id.termsText)
        termsTextView.setOnClickListener {
            val intent = Intent(this, TermsAndConditionsActivity::class.java)
            startActivity(intent)
        }
        val firstNameEditText = findViewById<EditText>(R.id.firstNameEditText)
        val lastNameEditText = findViewById<EditText>(R.id.lastNameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)
        val registerButton = findViewById<Button>(R.id.registerButton)



        registerButton.setOnClickListener {
            // Reset any previous error messages
            errorTextView.visibility = View.GONE

            // Validation
            if (!isValidEmail(emailEditText.text.toString())) {
                errorTextView.text = "Invalid Email Format."
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (!isPasswordsMatch(
                    passwordEditText.text.toString(),
                    confirmPasswordEditText.text.toString()
                )
            ) {
                errorTextView.text = "Passwords do not match."
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (!isValidPassword(passwordEditText.text.toString())) {
                errorTextView.text =
                    "Password must be at least 10 characters long, and contain letters, numbers, and symbols."
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }


        }

    }
    // Function to check if email is valid
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Function to check if passwords match
    fun isPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return (password == confirmPassword)
    }

    // Function to check if password is strong enough
    fun isValidPassword(password: String): Boolean {
        // At least 10 characters, with letters, numbers, and symbols
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,}$"
        return password.matches(passwordPattern.toRegex())
    }
}
