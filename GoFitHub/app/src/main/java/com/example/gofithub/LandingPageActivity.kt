package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class LandingPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)
        // Set up button click listeners to open LoginActivity
        findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            openLoginActivity()
        }

        findViewById<Button>(R.id.buttonRegister).setOnClickListener {
            openRegisterActivity()
        }
        findViewById<Button>(R.id.buttonContinueAsGuest).setOnClickListener {
            openGuestActivity()
        }
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun openRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    private fun openGuestActivity() {
        val intent = Intent(this, GuestActivity::class.java)
        startActivity(intent)
    }
}



