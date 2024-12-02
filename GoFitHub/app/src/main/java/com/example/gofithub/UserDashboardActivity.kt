package com.example.gofithub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.gofithub.database.AppDatabase
import kotlinx.coroutines.launch
import com.example.gofithub.SubscribeViewActivity
import com.example.gofithub.Activities

class UserDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_dashboard)

        val adImage = findViewById<ImageView>(R.id.adImage)
        val adPanel = findViewById<LinearLayout>(R.id.adPanel)
        val subscribeButton = findViewById<Button>(R.id.subscribeButton)
        val helloTextView = findViewById<TextView>(R.id.textViewHello)
        val activitiesButton = findViewById<Button>(R.id.activitiesButton)
        val friendsButton = findViewById<Button>(R.id.friendsButton)
        val trainingButton = findViewById<Button>(R.id.trainingButton)
        val plansButton = findViewById<Button>(R.id.plansButton)
        val recentPlanButton= findViewById<Button>(R.id.recent_plans)
        val recentPlansSection = findViewById<LinearLayout>(R.id.recentPlansSection)
        val recentGoalsText = findViewById<TextView>(R.id.recentGoalsText)

        val userDao = AppDatabase.getInstance(this).userDao()
        val goalsDao = AppDatabase.getInstance(this).goalsDao()

        val id = intent.getIntExtra("userId", -1)
        subscribeButton.visibility = View.GONE

        activitiesButton.setOnClickListener {
            val intent = Intent(this, Activities::class.java)
            intent.putExtra("userId", id)
            startActivity(intent)
        }

        lifecycleScope.launch {
            try {
                // Retrieve user from the database
                val user = userDao.getUserById(id)

                if (user != null) {
                    val goals = goalsDao.getLastAddedGoal(user.id)
                    val name = user.firstName
                    helloTextView.text = "Hello, $name!"
                    user.isSubscribed?.let {
                        if (!it) {
                            Glide.with(this@UserDashboardActivity)
                                .load("https://m.media-amazon.com/images/I/812NUDPv2LL._AC_SY695_.jpg")  // Replace with actual URL
                                .into(adImage)
                            subscribeButton.visibility = View.VISIBLE
                            subscribeButton.setOnClickListener {
                                val intent = Intent(
                                    this@UserDashboardActivity,
                                    SubscribeViewActivity::class.java
                                )
                                intent.putExtra("user_id", user.id)
                                startActivity(intent)
                            }
                        }
                    }


                    recentGoalsText.text = "Your most recent goals is to: ${goals?.goalText}"
                    recentPlanButton.setOnClickListener {
                        val intent = Intent(this@UserDashboardActivity, GoalsViewPage::class.java)
                        intent.putExtra("userId", id)
                        startActivity(intent)
                    }

                    trainingButton.setOnClickListener {
                        val intent = Intent(this@UserDashboardActivity, TrainersPage::class.java)
                        intent.putExtra("userId", id)
                        startActivity(intent)
                    }
                } else {
                    helloTextView.text = "Unknown User"
                }
            } catch (e: Exception) {
                Log.e("UserDashboardActivity", "Error retrieving user", e)
                Toast.makeText(
                    this@UserDashboardActivity,
                    "Error retrieving user",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            adPanel.setOnClickListener {
                //open link in the browser
                val url =
                    "https://www.amazon.com/wanhee-Running-Sneakers-Athletic-Breathable/dp/B0BFD2JVR8/ref=sr_1_4_sspa?crid=1CZS0DKDWNEMA&dib=eyJ2IjoiMSJ9.vUUNUC6sPcCMFiIeR8sBMYYkawYd8xljYedapw6N4jUZct7eJ0nsvs4RfdiEqemcd7rE1M6WpWBM7vQPMWZdJS4I5vRHk-zVo5gvsbeWh4OpjlXWOE2ccfzCFZ1YCc22G7ah-B4bkR2JY-oXbwpm8AZ_oU7JlgKtk_TWfxKS1gVuUhhLyaumxv7STKUik-drt3PpeGAe5J5pBUJiBfNK8ytL4jAKKhAfE7_E1jADWYj2R-JHJ0lOdE6eEKtNLd7mC-YHOst6dg0hNNJ_sReWSOx-ZQ9FncOyUOrZ_Ek22cY.sQGUQ2u7HMok8SwBzwrmKXmSkNOjV9esW4F-5SwdZ8U&dib_tag=se&keywords=running+shoes+for+men&qid=1732098086&sprefix=running%2Caps%2C347&sr=8-4-spons&sp_csd=d2lkZ2V0TmFtZT1zcF9hdGY&psc=1"
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url))
                // Check if there's an activity that can handle this intent
                startActivity(intent)  // Start the activity to open the URL
            }
//            friendsButton.setOnClickListener {
//                val intent = Intent(this@UserDashboardActivity, FriendsViewPage::class.java)
//                intent.putExtra("userId", id)
//                startActivity(intent)
//            }
        }


    }

}
