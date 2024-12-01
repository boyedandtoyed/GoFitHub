package com.example.gofithub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var lineChartActivities: LineChart
    private lateinit var barChartDuration: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_report_view)

        // Initialize charts
        barChartCalories = findViewById(R.id.barChartCalories)
        lineChartActivities = findViewById(R.id.lineChartActivities)
        barChartDuration = findViewById(R.id.barChartDuration)

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

            val weeklyDurations = last10WeeksData.groupBy { it.getWeekOfYear() }
                .mapValues { it.value.sumOf { activity -> activity.duration } }

            withContext(Dispatchers.Main) {
                setupCaloriesBarChart(weeklyCalories)
                setupActivitiesLineChart(weeklyActivitiesCount)
                setupDurationBarChart(weeklyDurations)
            }
        }
    }

    private fun setupCaloriesBarChart(weeklyData: Map<Int, Int>) {
        val entries = weeklyData.map { BarEntry(it.key.toFloat(), it.value.toFloat()) }
        val dataSet = BarDataSet(entries, "Calories Burned per Week")
        barChartCalories.data = BarData(dataSet)
        barChartCalories.description.text = "Weekly Calories Burned"
        barChartCalories.invalidate() // Refresh chart
    }

    private fun setupActivitiesLineChart(weeklyData: Map<Int, Int>) {
        val entries = weeklyData.map { Entry(it.key.toFloat(), it.value.toFloat()) }
        val dataSet = LineDataSet(entries, "Activities Completed per Week")
        lineChartActivities.data = LineData(dataSet)
        lineChartActivities.description.text = "Weekly Activities Progress"
        lineChartActivities.invalidate() // Refresh chart
    }

    private fun setupDurationBarChart(weeklyData: Map<Int, Int>) {
        val entries = weeklyData.map { BarEntry(it.key.toFloat(), it.value.toFloat()) }
        val dataSet = BarDataSet(entries, "Duration per Week (Minutes)")
        barChartDuration.data = BarData(dataSet)
        barChartDuration.description.text = "Weekly Activity Durations"
        barChartDuration.invalidate() // Refresh chart
    }


    // Helper Extension
    fun UserActivity.getWeekOfYear(): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(this.activityCompletedDate)
        val calendar = Calendar.getInstance().apply { time = date!! }
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }
}