package com.example.gofithub

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts


class UserRegisterActivity : AppCompatActivity() {

    private var profileImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        // Set up the goal type Spinner
        val goalTypeSpinner = findViewById<Spinner>(R.id.goalTypeSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.goal_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            goalTypeSpinner.adapter = adapter
        }

        // Get references to EditTexts
        val firstNameEditText = findViewById<EditText>(R.id.firstNameEditText)
        val lastNameEditText = findViewById<EditText>(R.id.lastNameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val dobEditButton = findViewById<Button>(R.id.dobEditText)
        val weightEditText = findViewById<EditText>(R.id.weightEditText)
        val heightEditText = findViewById<EditText>(R.id.heightEditText)
        val bioEditText = findViewById<EditText>(R.id.goalExplanationEditText)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)
        val registerButton = findViewById<Button>(R.id.registerButton)


        //Date picker dialog box open for dobEditButton onClickListener
        dobEditButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    // Update the dobEditButton text with the selected date
                    val selectedDate = "$dayOfMonth/${month + 1}/$year"
                    dobEditButton.text = selectedDate
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }


        // On Click Listener for Register Button
        registerButton.setOnClickListener {

            // Reset any previous error messages
            errorTextView.visibility = View.GONE

            // Validate the fields
            if (!isValidEmail(emailEditText.text.toString())) {
                errorTextView.text = "Invalid Email Format."
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (!isPasswordsMatch(passwordEditText.text.toString(), confirmPasswordEditText.text.toString())) {
                errorTextView.text = "Passwords don't match."
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if (!isValidPassword(passwordEditText.text.toString())) {
                errorTextView.text = "Password must be at least 10 characters long and include at least one character and alphanumeric character."
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    // check if password has at least 10 characters and include at least one character and alphanumeric character
    private fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
        return password.matches(passwordRegex.toRegex())
    }

}
