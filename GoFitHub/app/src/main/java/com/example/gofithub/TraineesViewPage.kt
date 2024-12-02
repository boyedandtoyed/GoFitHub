package com.example.gofithub

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.gofithub.database.AppDatabase
import kotlinx.coroutines.launch

class TraineesViewPage : AppCompatActivity() {

    private lateinit var traineesListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainees_view_page)

        traineesListLayout = findViewById(R.id.traineesListLayout)

        val trainerId = intent.getIntExtra("trainerId", -1)
        Log.d("Trainer ID is----", trainerId.toString())
        val trainerTraineeRelationDao = AppDatabase.getInstance(this).trainerTraineeRelationDao()
        val userDao = AppDatabase.getInstance(this).userDao()

        lifecycleScope.launch {
            try {
                val trainees = trainerTraineeRelationDao.getTraineesForTrainer(trainerId)


                if (trainees.isNotEmpty()) {
                    for (trainee in trainees) {
                        val user = userDao.getUserById(trainee.traineeId)

                        val traineeView = TextView(this@TraineesViewPage).apply {
                            text = "Name: ${user?.firstName} ${user?.lastName}\n" +
                                    "Training: ${trainee.trainingName}"
                            textSize = 16f
                            setPadding(16, 16, 16, 16)
                        }
                        traineesListLayout.addView(traineeView)
                    }
                } else {
                    Toast.makeText(this@TraineesViewPage, "No trainees found", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@TraineesViewPage, "Error loading trainees", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
