package com.example.gofithub

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.UserActivity
import com.example.gofithub.database.UserActivityDao
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class ProgressReportView : AppCompatActivity() {

    private lateinit var userActivityDao: UserActivityDao
    private lateinit var barChartCalories: BarChart
    private lateinit var barChartActivities: BarChart
    private lateinit var barChartDuration: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_report_view)

        // Initialize charts
        barChartCalories = findViewById(R.id.barChartCalories)
        barChartActivities = findViewById(R.id.barChartActivities)
        barChartDuration = findViewById(R.id.barChartDuration)
        userActivityDao = AppDatabase.getInstance(this).userActivityDao()

        // Get userId from intent
        val userId = intent.getIntExtra("userId", -1)

        // Load progress data
        CoroutineScope(Dispatchers.IO).launch {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.WEEK_OF_YEAR, -10)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDate = sdf.format(calendar.time)

            val last10WeeksData = userActivityDao.getLast10WeeksData(userId, startDate)

            val weeklyCalories = last10WeeksData.groupBy { it.getWeekOfYear() }
                .mapValues { it.value.sumOf { activity -> activity.caloriesBurned } }

            val weeklyActivitiesCount = last10WeeksData.groupBy { it.getWeekOfYear() }
                .mapValues { it.value.size }
            Log.d("ProgressReportView---", "Weekly Activities Count: $weeklyActivitiesCount")

            val weeklyDurations = last10WeeksData.groupBy { it.getWeekOfYear() }
                .mapValues { it.value.sumOf { activity -> activity.duration } }


            withContext(Dispatchers.Main) {
                setupCaloriesBarChart(weeklyCalories)
                setupActivitiesBarChart(weeklyActivitiesCount)
                setupDurationBarChart(weeklyDurations)
            }
        }
    }

    private fun setupCaloriesBarChart(weeklyData: Map<Int, Int>) {
        val entries = weeklyData.map { BarEntry(it.key.toFloat(), it.value.toFloat()) }
        val dataSet = BarDataSet(entries, "Calories Burned per Week")

        // Set color to red
        dataSet.color = Color.RED

        // Customize the Y-axis to make it more stretched
        val yAxis = barChartCalories.axisLeft
        yAxis.axisMinimum = 0f  // Set minimum Y value (optional)
        yAxis.axisMaximum = (weeklyData.values.maxOrNull() ?: 100) * 1.2f  // Add 20% extra space

        barChartCalories.data = BarData(dataSet)
        barChartCalories.description.text = "Weekly Calories Burned"
        barChartCalories.notifyDataSetChanged()
        barChartCalories.invalidate()

    }

    private fun setupActivitiesBarChart(weeklyData: Map<Int, Int>) {
        val entries = weeklyData.map { BarEntry(it.key.toFloat(), it.value.toFloat()) }
        val dataSet = BarDataSet(entries, "Activities Completed per Week")
        Log.d("ProgressReportView---", "entries: $entries")

        // Set color for the bar chart (e.g., blue for activities)
        dataSet.color = Color.BLUE

        // Customize the Y-axis
        val yAxis = barChartActivities.axisLeft
        yAxis.axisMinimum = 0f  // Set minimum Y value
        yAxis.axisMaximum = (weeklyData.values.maxOrNull() ?: 10) * 1.2f  // Add 20% extra space
        yAxis.textColor = Color.BLACK

        barChartActivities.axisRight.isEnabled = false  // Disable the right Y-axis
        barChartActivities.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChartActivities.xAxis.setDrawGridLines(false)
        barChartActivities.xAxis.textColor = Color.BLACK

        barChartActivities.data = BarData(dataSet)
        barChartActivities.description.text = "Weekly Activities Progress"
        barChartActivities.description.textColor = Color.BLACK
        barChartActivities.setFitBars(true)  // Adjust the bars to fit properly
        barChartActivities.invalidate()  // Refresh the chart
    }






    private fun setupDurationBarChart(weeklyData: Map<Int, Int>) {
        val entries = weeklyData.map { BarEntry(it.key.toFloat(), it.value.toFloat()) }
        val dataSet = BarDataSet(entries, "Duration per Week (Minutes)")

        // Set color to yellow
        dataSet.color = Color.YELLOW

        // Customize the Y-axis to make it more stretched
        val yAxis = barChartDuration.axisLeft
        yAxis.axisMinimum = 0f  // Set minimum Y value (optional)
        yAxis.axisMaximum = (weeklyData.values.maxOrNull() ?: 30) * 1.2f  // Add 20% extra space

        barChartDuration.data = BarData(dataSet)
        barChartDuration.description.text = "Weekly Activity Durations"
        barChartDuration.notifyDataSetChanged()
        barChartDuration.invalidate()

    }



    // Helper Extension
    fun UserActivity.getWeekOfYear(): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(this.activityCompletedDate)
        val calendar = Calendar.getInstance().apply { time = date!! }
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }
}