package com.example.gofithub

import android.content.Intent
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

class TrainersPage : AppCompatActivity() {

    private lateinit var paidTrainersLayout: LinearLayout
    private lateinit var availableTrainersLayout: LinearLayout
    private lateinit var trainerDao: TrainerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainers_page)

        paidTrainersLayout = findViewById(R.id.paidTrainersLayout)
        availableTrainersLayout = findViewById(R.id.availableTrainersLayout)

        trainerDao = AppDatabase.getInstance(this).trainerDao()

        // Fetch and display paid trainers
        CoroutineScope(Dispatchers.IO).launch {
            val paidTrainers = getPaidTrainers() // Get paid trainers from DB
            withContext(Dispatchers.Main) {
                for (trainer in paidTrainers) {
                    addTrainerView(trainer, true)
                }
            }
        }

        // Fetch and display available trainers
        CoroutineScope(Dispatchers.IO).launch {
            val availableTrainers = trainerDao.getAllTrainers()
            withContext(Dispatchers.Main) {
                for (trainer in availableTrainers) {
                    addTrainerView(trainer, false)
                }
            }
        }
    }

    private suspend fun getPaidTrainers(): List<Trainer> {
        // Fetch the trainers the user has paid for from the database
        return trainerDao.getAllTrainers() // For now, just fetch all trainers; later filter by paid status
    }

    private fun addTrainerView(trainer: Trainer, isPaid: Boolean) {
        val trainerLayout = LinearLayout(this)
        trainerLayout.orientation = LinearLayout.HORIZONTAL
        trainerLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val trainerInfoLayout = LinearLayout(this)
        trainerInfoLayout.orientation = LinearLayout.VERTICAL
        trainerInfoLayout.layoutParams = LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
        )

        val trainerNameTextView = TextView(this)
        trainerNameTextView.text = trainer.firstName + " " + trainer.lastName
        trainerNameTextView.textSize = 18f
        trainerNameTextView.setTypeface(null, Typeface.BOLD)

        val trainerSpecialtyTextView = TextView(this)
        trainerSpecialtyTextView.text = "Speciality: ${trainer.specialty}"
        trainerSpecialtyTextView.textSize = 14f

        val trainerExperienceTextView = TextView(this)
        trainerExperienceTextView.text = "Experience: ${trainer.experienceLevel}"
        trainerExperienceTextView.textSize = 14f

        val trainerBioTextView = TextView(this)
        trainerBioTextView.text = "Bio: ${trainer.bio}"
        trainerBioTextView.textSize = 12f
        trainerBioTextView.maxLines = 2
        trainerBioTextView.ellipsize = TextUtils.TruncateAt.END

        trainerInfoLayout.addView(trainerNameTextView)
        trainerInfoLayout.addView(trainerSpecialtyTextView)
        trainerInfoLayout.addView(trainerExperienceTextView)
        trainerInfoLayout.addView(trainerBioTextView)

        val rateButton = Button(this)
        rateButton.text = "Rate: ${trainer.hourlyRate}/hr"
//        rateButton.setOnClickListener {
//            // Redirect to the payment page when the button is clicked
//            val intent = Intent(this, TrainerPaymentPage::class.java)
//            intent.putExtra("trainerId", trainer.id)
//            startActivity(intent)
//        }

        // Add the views to the layout
        trainerLayout.addView(trainerInfoLayout)

        // If the trainer is paid, add to paid trainers section
        if (isPaid) {
            paidTrainersLayout.addView(trainerLayout)
        } else {
            // Otherwise, add to available trainers section
            trainerLayout.addView(rateButton)
            availableTrainersLayout.addView(trainerLayout)
        }
    }
}
