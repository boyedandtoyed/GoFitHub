package com.example.gofithub

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class UserRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        val goalTypeSpinner = findViewById<Spinner>(R.id.goalTypeSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.goal_types, // Array defined in strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            goalTypeSpinner.adapter = adapter
        }
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
        val dobEditText = findViewById<EditText>(R.id.dobEditText)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)
        val registerButton = findViewById<Button>(R.id.registerButton)

        //dialog box for date of birth
        dobEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "${selectedMonth + 1}/${selectedDay}/${selectedYear}"
                    dobEditText.setText(formattedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

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
