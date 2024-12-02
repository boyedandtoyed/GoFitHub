package com.example.gofithub

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.Trainer
import com.example.gofithub.database.TrainerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.gofithub.R
import android.text.TextUtils
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.gofithub.database.TrainerTraineeRelationDao

class TrainersPage : AppCompatActivity() {

    private lateinit var paidTrainersLayout: LinearLayout
    private lateinit var availableTrainersLayout: LinearLayout
    private lateinit var trainerDao: TrainerDao
    private lateinit var trainerTraineeRelationDao: TrainerTraineeRelationDao

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainers_page)

        paidTrainersLayout = findViewById(R.id.paidTrainersLayout)
        availableTrainersLayout = findViewById(R.id.availableTrainersLayout)
        userId = intent.getIntExtra("userId", -1)

        trainerDao = AppDatabase.getInstance(this).trainerDao()
        trainerTraineeRelationDao = AppDatabase.getInstance(this).trainerTraineeRelationDao()

        // Fetch and display paid trainers
        CoroutineScope(Dispatchers.IO).launch {
            val paidTrainers = getPaidTrainers(userId) // Get paid trainers from DB
            withContext(Dispatchers.Main) {
                for (trainer in paidTrainers) {
                    addTrainerView(trainer, true)
                }
            }
        }

        // Fetch and display available trainers
        CoroutineScope(Dispatchers.IO).launch {
            val availableTrainers = trainerDao.getAvailableTrainers()
            withContext(Dispatchers.Main) {
                for (trainer in availableTrainers) {
                    addTrainerView(trainer, false)
                }
            }
        }
    }

    private suspend fun getPaidTrainers(userId: Int): List<Trainer> {
        // Fetch the trainers the user has paid for from the database
        val trainerRelations = trainerTraineeRelationDao.getTrainersForTrainee(userId)
        val trainerIds = trainerRelations.map { it.trainerId }
        return trainerDao.getTrainersByIds(trainerIds)

    }
    private suspend fun getAvailableTrainers(): List<Trainer> {
        // Fetch the available trainers from the database
        return trainerDao.getAvailableTrainers()
    }

    private fun addTrainerView(trainer: Trainer, isPaid: Boolean) {
        val buttonColor = ContextCompat.getColor(this, R.color.button_color)
        val buttonTextColor = ContextCompat.getColor(this, R.color.button_text_color)
        val blackColor = Color.BLACK

        val trainerLayout = LinearLayout(this)
        trainerLayout.orientation = LinearLayout.HORIZONTAL
        trainerLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        trainerLayout.setPadding(0, 0, 0, 16)  // Space between trainers

        val trainerInfoLayout = LinearLayout(this)
        trainerInfoLayout.orientation = LinearLayout.VERTICAL
        trainerInfoLayout.layoutParams = LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
        )

        // Trainer Name and Details
        val trainerNameTextView = TextView(this)
        trainerNameTextView.text = "${trainer.firstName} ${trainer.lastName}"
        trainerNameTextView.textSize = 18f
        trainerNameTextView.setTypeface(null, Typeface.BOLD)
        trainerNameTextView.setTextColor(blackColor)

        val trainerSpecialtyTextView = TextView(this)
        trainerSpecialtyTextView.text = "Specialty: ${trainer.specialty}"
        trainerSpecialtyTextView.textSize = 14f
        trainerSpecialtyTextView.setTextColor(blackColor)

        val trainerExperienceTextView = TextView(this)
        trainerExperienceTextView.text = "Experience: ${trainer.experienceLevel}"
        trainerExperienceTextView.textSize = 14f
        trainerExperienceTextView.setTextColor(blackColor)

        val trainerBioTextView = TextView(this)
        trainerBioTextView.text = "Bio: ${trainer.bio}"
        trainerBioTextView.textSize = 12f
        trainerBioTextView.maxLines = 2
        trainerBioTextView.ellipsize = TextUtils.TruncateAt.END
        trainerBioTextView.setTextColor(blackColor)

        trainerInfoLayout.addView(trainerNameTextView)
        trainerInfoLayout.addView(trainerSpecialtyTextView)
        trainerInfoLayout.addView(trainerExperienceTextView)
        trainerInfoLayout.addView(trainerBioTextView)

        // Button Section
        if (isPaid) {
            val rateButton = Button(this)
            rateButton.text = "Rate Trainer"
            rateButton.setBackgroundColor(buttonColor)
            rateButton.setTextColor(buttonTextColor)
            rateButton.setOnClickListener {
                // Add functionality for rating trainer
            }
            trainerLayout.addView(trainerInfoLayout)
            trainerLayout.addView(rateButton)
            paidTrainersLayout.addView(trainerLayout)
        } else {
            val hourlyRateTextView = TextView(this)
            hourlyRateTextView.text = "Hourly Rate: ${trainer.hourlyRate}/hr"
            hourlyRateTextView.textSize = 14f
            hourlyRateTextView.setTextColor(blackColor)

            val availabilityTextView = TextView(this)
            availabilityTextView.text = "Availability: ${if (trainer.isAvailable) "Available" else "Not Available"}"
            availabilityTextView.textSize = 14f
            availabilityTextView.setTextColor(blackColor)

            val hireButton = Button(this)
            hireButton.text = "Hire This Trainer"
            hireButton.setBackgroundColor(buttonColor)
            hireButton.setTextColor(buttonTextColor)
            hireButton.isEnabled = trainer.isAvailable
            hireButton.setPadding(30,10,30,10)
            hireButton.setOnClickListener {
                // Add functionality for hiring trainer
                val intent = Intent(this, TrainerPaymentPage::class.java)
                intent.putExtra("trainerId", trainer.id)
                intent.putExtra("userId", userId)
                intent.putExtra("hourlyRate", trainer.hourlyRate)
                intent.putExtra("trainerName", "${trainer.firstName} ${trainer.lastName}")

                startActivity(intent)
            }

            trainerInfoLayout.addView(hourlyRateTextView)
            trainerInfoLayout.addView(availabilityTextView)

            trainerLayout.addView(trainerInfoLayout)
            trainerLayout.addView(hireButton)
            availableTrainersLayout.addView(trainerLayout)
        }
    }


}
