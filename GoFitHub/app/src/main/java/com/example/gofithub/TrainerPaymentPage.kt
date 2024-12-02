package com.example.gofithub

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gofithub.SubscribeViewActivity
import com.example.gofithub.database.AppDatabase
import com.example.gofithub.database.TrainerTraineeRelation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrainerPaymentPage : AppCompatActivity() {

    private lateinit var creditCardDetails: LinearLayout
    private lateinit var paypalLoginButton: Button
    private lateinit var hireButton: Button
    private lateinit var creditCardNumber: EditText
    private lateinit var securityCode: EditText
    private lateinit var expirationDate: EditText
    private lateinit var paymentOptions: RadioGroup
    private lateinit var trainingName: EditText
    private lateinit var trainerPaymentTitle: TextView
    private lateinit var paymentAmount: TextView

    private var userId: Int = -1
    private var trainerId: Int = -1
    private var trainerHourlyRate: Double = 0.0
    private var trainerName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trainer_payment_page)

        userId = intent.getIntExtra("user_id", -1)
        trainerId = intent.getIntExtra("trainer_id", -1)

        paymentOptions = findViewById(R.id.payment_options)
        creditCardDetails = findViewById(R.id.credit_card_details)
        paypalLoginButton = findViewById(R.id.paypal_login_button)
        hireButton = findViewById(R.id.hire_button)
        creditCardNumber = findViewById(R.id.credit_card_number)
        securityCode = findViewById(R.id.security_code)
        expirationDate = findViewById(R.id.expiration_date)
        trainingName = findViewById(R.id.training_name_title)
        trainerHourlyRate = intent.getDoubleExtra("hourly_rate", 0.0)
        trainerName = intent.getStringExtra("trainer_name") ?: ""
        trainerPaymentTitle = findViewById(R.id.trainer_payment_title)
        paymentAmount = findViewById(R.id.payment_amount)
        paymentAmount.text = "Price: $trainerHourlyRate"


        // Disable subscribe button initially
        hireButton.isEnabled = false
        hireButton.setBackgroundTintList(getColorStateList(R.color.grey))

        paymentOptions.setOnCheckedChangeListener { _, checkedId ->
            hireButton.isEnabled = true
            hireButton.setBackgroundTintList(getColorStateList(android.R.color.holo_blue_dark))

            when (checkedId) {
                R.id.debit_credit_option -> {
                    creditCardDetails.visibility = View.VISIBLE
                    paypalLoginButton.visibility = View.GONE
                }

                R.id.paypal_option -> {
                    creditCardDetails.visibility = View.GONE
                    paypalLoginButton.visibility = View.VISIBLE
                }
            }
        }

        // Set trainer payment title
        trainerPaymentTitle.text = "Hire, $trainerName"


        // PayPal button logic
        paypalLoginButton.setOnClickListener {
            Toast.makeText(this, "Redirecting to PayPal...", Toast.LENGTH_SHORT).show()
            makeRelationship(trainerId, userId)
        }

        // Subscribe button logic
        hireButton.setOnClickListener {
            if (paymentOptions.checkedRadioButtonId == R.id.debit_credit_option) {
                if (validateCreditCardDetails()) {
                    makeRelationship(trainerId, userId)
                } else {
                    Toast.makeText(this, "Please fill all credit card details.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    // Validate credit card details
    private fun validateCreditCardDetails(): Boolean {
        val cardNumber = creditCardNumber.text.toString().trim()
        val security = securityCode.text.toString().trim()
        val expiry = expirationDate.text.toString().trim()

        if (cardNumber.length != 16 || !TextUtils.isDigitsOnly(cardNumber)) {
            Toast.makeText(this, "Invalid Credit Card Number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (security.length != 3 || !TextUtils.isDigitsOnly(security)) {
            Toast.makeText(this, "Invalid Security Code", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!expiry.matches(Regex("\\d{2}/\\d{2}"))) {
            Toast.makeText(this, "Invalid Expiration Date", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    // Simulate user subscription
    private fun makeRelationship(trainerId: Int, userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val appDatabase = AppDatabase.getInstance(applicationContext)
            val trainerTraineeRelationDao = appDatabase.trainerTraineeRelationDao()
            // calculate current date
            val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())


            //save relationship
            val trainerTraineeRelation = TrainerTraineeRelation(
                trainerId=trainerId,
                traineeId = userId,
                paymentDate = currentDate,
                trainingName = trainingName.text.toString(),
                paymentAmount = trainerHourlyRate,
            )
            trainerTraineeRelationDao.insertRelation(trainerTraineeRelation)

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@TrainerPaymentPage,
                    "You're Subscribed to Trainer",
                    Toast.LENGTH_LONG
                ).show()
                finish() // Close activity
            }
        }

    }
}