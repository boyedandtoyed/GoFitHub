package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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

    }
}
