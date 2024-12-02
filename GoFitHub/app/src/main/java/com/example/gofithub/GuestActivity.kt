package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.gofithub.UserDashboardActivity
import com.example.gofithub.database.AppDatabase

class GuestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guest)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val adImage = findViewById<ImageView>(R.id.adImage)
        val adPanel = findViewById<LinearLayout>(R.id.adPanel)
        val activitiesButton = findViewById<Button>(R.id.activitiesButton)
        val friendsButton = findViewById<Button>(R.id.friendsButton)

        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        Glide.with(this@GuestActivity)
            .load("https://m.media-amazon.com/images/I/812NUDPv2LL._AC_SY695_.jpg")  // Replace with actual URL
            .into(adImage)
        adPanel.setOnClickListener {
            //open link in the browser
            val url =
                "https://www.amazon.com/wanhee-Running-Sneakers-Athletic-Breathable/dp/B0BFD2JVR8/ref=sr_1_4_sspa?crid=1CZS0DKDWNEMA&dib=eyJ2IjoiMSJ9.vUUNUC6sPcCMFiIeR8sBMYYkawYd8xljYedapw6N4jUZct7eJ0nsvs4RfdiEqemcd7rE1M6WpWBM7vQPMWZdJS4I5vRHk-zVo5gvsbeWh4OpjlXWOE2ccfzCFZ1YCc22G7ah-B4bkR2JY-oXbwpm8AZ_oU7JlgKtk_TWfxKS1gVuUhhLyaumxv7STKUik-drt3PpeGAe5J5pBUJiBfNK8ytL4jAKKhAfE7_E1jADWYj2R-JHJ0lOdE6eEKtNLd7mC-YHOst6dg0hNNJ_sReWSOx-ZQ9FncOyUOrZ_Ek22cY.sQGUQ2u7HMok8SwBzwrmKXmSkNOjV9esW4F-5SwdZ8U&dib_tag=se&keywords=running+shoes+for+men&qid=1732098086&sprefix=running%2Caps%2C347&sr=8-4-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&psc=1"
            val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url))
            // Check if there's an activity that can handle this intent
            startActivity(intent)  // Start the activity to open the URL
        }

        activitiesButton.setOnClickListener {
            val intent = Intent(this, Activities::class.java)

            startActivity(intent)
        }

    }
}
