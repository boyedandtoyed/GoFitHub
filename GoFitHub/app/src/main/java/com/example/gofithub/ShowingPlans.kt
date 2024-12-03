package com.example.gofithub

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.gofithub.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.gofithub.R
import com.example.gofithub.database.CreateWorkout

class ShowingPlans : AppCompatActivity() {

    private lateinit var videosLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showing_plans)

        videosLayout = findViewById(R.id.videosLayout)

        val trainerId = intent.getIntExtra("trainerId", -1)
        val workoutDao = AppDatabase.getInstance(this).createWorkoutDao()


        // Fetch workout videos from the database
        CoroutineScope(Dispatchers.IO).launch {
            if (trainerId == -1) {
                withContext(Dispatchers.Main) {
                    for (workout in workoutDao.getAllWorkouts()) {
                        addVideoToLayout(workout.youtubeLink)
                    }

                }
            }
                else{
                    withContext(Dispatchers.Main) {
                        for (workout in workoutDao.getWorkoutsByTrainerId(trainerId)) {
                            addVideoToLayout(workout.youtubeLink)
                        }
                    }

                }

        }
    }

    private fun addVideoToLayout(youtubeLink: String) {
        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // Embed the YouTube video
        val videoHtml = """
            <html>
            <body>
            <iframe width="100%" height="100%" src="https://www.youtube.com/embed/${extractVideoId(youtubeLink)}" 
            frameborder="0" allowfullscreen></iframe>
            </body>
            </html>
        """
        webView.loadData(videoHtml, "text/html", "utf-8")

        // Add WebView to the LinearLayout
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            600 // Height in pixels
        )
        layoutParams.setMargins(0, 16, 0, 16)
        webView.layoutParams = layoutParams
        videosLayout.addView(webView)
    }

    private fun extractVideoId(url: String): String {
        return url.split("v=")[1].split("&")[0] // Basic logic; improve if needed for other URL formats
    }
}
