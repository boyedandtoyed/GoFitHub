package com.example.gofithub

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.Goals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GoalsViewPage : AppCompatActivity() {
    private lateinit var incompleteGoalsContainer: LinearLayout
    private lateinit var completedGoalsContainer: LinearLayout
    private lateinit var goalTextInput: EditText
    private lateinit var durationInput: EditText
    private lateinit var caloriesTargetInput: EditText
    private lateinit var addGoalButton: Button
    private lateinit var markAsCompletedButton: Button

    private val userId: Int by lazy { intent.getIntExtra("userId", -1) }

    private val selectedGoalIds = mutableListOf<Int>() // To keep track of selected goals

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals_view_page)

        // Initialize UI elements
        incompleteGoalsContainer = findViewById(R.id.incompleteGoalsContainer)
        completedGoalsContainer = findViewById(R.id.completedGoalsContainer)
        goalTextInput = findViewById(R.id.goalTextInput)
        durationInput = findViewById(R.id.durationInput)
        caloriesTargetInput = findViewById(R.id.caloriesTargetInput)
        addGoalButton = findViewById(R.id.addGoalButton)
        markAsCompletedButton = findViewById(R.id.markAsCompletedButton)

        // Load data
        loadGoals()

        // Add goal logic
        addGoalButton.setOnClickListener {
            val goalText = goalTextInput.text.toString()
            val duration = durationInput.text.toString().toIntOrNull() ?: 0
            val caloriesTarget = caloriesTargetInput.text.toString().toIntOrNull() ?: 0

            addNewGoal(goalText, duration, caloriesTarget)
        }

        // Mark selected goals as completed
        markAsCompletedButton.setOnClickListener {
            markGoalsAsCompleted(selectedGoalIds)
        }
    }

    private fun loadGoals() {
        val goalsDao = AppDatabase.getInstance(this).goalsDao()

        CoroutineScope(Dispatchers.IO).launch {
            val incompleteGoals = goalsDao.getIncompleteGoals(userId)
            val completedGoals = goalsDao.getLast10CompletedGoals(userId)

            withContext(Dispatchers.Main) {
                populateGoals(incompleteGoals, incompleteGoalsContainer)
                populateGoals(completedGoals, completedGoalsContainer)
            }
        }
    }

    private fun populateGoals(goals: List<Goals>, container: LinearLayout) {
        container.removeAllViews()
        goals.forEach { goal ->
            val goalLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 8)
            }

            val goalTextView = TextView(this).apply {
                text = goal.goalText
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val checkBox = CheckBox(this).apply {
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedGoalIds.add(goal.id) // Add goal to selected list
                    } else {
                        selectedGoalIds.remove(goal.id) // Remove goal from selected list
                    }
                }
            }

            goalLayout.addView(goalTextView)
            goalLayout.addView(checkBox)
            container.addView(goalLayout)
        }
    }

    private fun addNewGoal(goalText: String, duration: Int, caloriesTarget: Int) {
        val goalsDao = AppDatabase.getInstance(this).goalsDao()
        //get current date and time in string format like this 2024-11-23 11:50:00
        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())


        val newGoal = Goals(userId = userId, goalText = goalText, goalCompletedDate = currentDate, duration = duration, caloriesTarget = caloriesTarget)

        CoroutineScope(Dispatchers.IO).launch {
            goalsDao.insertGoals(newGoal)
            loadGoals()
        }
    }

    private fun markGoalsAsCompleted(goalIds: List<Int>) {
        val goalsDao = AppDatabase.getInstance(this).goalsDao()

        CoroutineScope(Dispatchers.IO).launch {
            goalIds.forEach { goalId ->
                goalsDao.setGoalCompleted(goalId)
            }
            withContext(Dispatchers.Main) {
                loadGoals() // Reload the goals to reflect changes
            }
        }
    }
}
