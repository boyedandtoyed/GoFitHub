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
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import database.DatabaseHelper

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
        val dobEditText = findViewById<EditText>(R.id.dobEditText)
        val weightEditText = findViewById<EditText>(R.id.weightEditText)
        val heightEditText = findViewById<EditText>(R.id.heightEditText)
        val bioEditText = findViewById<EditText>(R.id.bioEditText)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)
        val registerButton = findViewById<Button>(R.id.registerButton)

        val uploadProfilePictureButton = findViewById<Button>(R.id.buttonUploadPicture)

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
        // Image picker activity result callback
        val pickImageResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            profileImageUri = uri
            Toast.makeText(this, "Profile picture selected!", Toast.LENGTH_SHORT).show()
        }

        uploadProfilePictureButton.setOnClickListener {
            // Trigger image picker to allow the user to select a profile picture
            pickImageResult.launch("image/*")
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

            // All fields are validated, save the user's data
            val databaseHelper = DatabaseHelper(this)

            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val dob = dobEditText.text.toString()
            val weight = weightEditText.text.toString().toDoubleOrNull() ?: 0.0
            val height = heightEditText.text.toString().toDoubleOrNull() ?: 0.0
            val goalType = goalTypeSpinner.selectedItem.toString()
            val bio = bioEditText.text.toString()


            // Save profile image URI if available
            val profilePicturePath = profileImageUri?.toString() ?: ""

            // Insert user data into the database
            val result = databaseHelper.addUser(firstName, lastName, email, password, dob, weight, height, goalType, bio, profilePicturePath)

            if (result == -1L) {
                errorTextView.text = "Failed to register user."
                errorTextView.visibility = View.VISIBLE
            } else {
                // Redirect to next activity (User's Dashboard or Home)
                val intent = Intent(this, UserDashboardActivity::class.java)
                startActivity(intent)
                finish()
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
