package com.example.gofithub

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


class TrainerRegisterActivity : AppCompatActivity() {

    private var profileImageUri: Uri? = null
    private var certificateUri: Uri? = null

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

        // Get references to EditTexts and other components
        val firstNameEditText = findViewById<EditText>(R.id.firstNameEditText)
        val lastNameEditText = findViewById<EditText>(R.id.lastNameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val bioEditText = findViewById<EditText>(R.id.bioEditText)
        val hourlyRateEditText = findViewById<EditText>(R.id.hourlyRateEditText)

        val uploadProfilePictureButton = findViewById<Button>(R.id.buttonUploadPicture)
        val uploadCertificateButton = findViewById<Button>(R.id.buttonUploadCertificate)

        // Image picker activity result callback for Profile Picture
        val pickProfileImageResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            profileImageUri = uri
            Toast.makeText(this, "Profile picture selected!", Toast.LENGTH_SHORT).show()
        }

        // Image picker activity result callback for Certificate
        val pickCertificateResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            certificateUri = uri
            Toast.makeText(this, "Certificate selected!", Toast.LENGTH_SHORT).show()
        }

        // Handle Profile Picture Upload Button
        uploadProfilePictureButton.setOnClickListener {
            // Trigger image picker to allow the trainer to select a profile picture
            pickProfileImageResult.launch("image/*")
        }

        // Handle Certificate Upload Button
        uploadCertificateButton.setOnClickListener {
            // Trigger image picker to allow the trainer to select a certificate
            pickCertificateResult.launch("application/pdf")
        }

        // On Click Listener for Register Button
        registerButton.setOnClickListener {

            // Reset any previous error messages
            errorTextView.visibility = View.GONE

            // Validation
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
