package com.example.gofithub

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gofithub.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscribeViewActivity : AppCompatActivity() {

    private lateinit var paymentOptions: RadioGroup
    private lateinit var creditCardDetails: LinearLayout
    private lateinit var paypalLoginButton: Button
    private lateinit var subscribeButton: Button
    private lateinit var creditCardNumber: EditText
    private lateinit var securityCode: EditText
    private lateinit var expirationDate: EditText

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe_view)

        // Get user ID from intent
        userId = intent.getIntExtra("user_id", -1)

        // Initialize UI elements
        paymentOptions = findViewById(R.id.payment_options)
        creditCardDetails = findViewById(R.id.credit_card_details)
        paypalLoginButton = findViewById(R.id.paypal_login_button)
        subscribeButton = findViewById(R.id.subscribe_button)
        creditCardNumber = findViewById(R.id.credit_card_number)
        securityCode = findViewById(R.id.security_code)
        expirationDate = findViewById(R.id.expiration_date)

        // Disable subscribe button initially
        subscribeButton.isEnabled = false
        subscribeButton.setBackgroundTintList(getColorStateList(R.color.grey))

        // Payment option selection logic
        paymentOptions.setOnCheckedChangeListener { _, checkedId ->
            subscribeButton.isEnabled = true
            subscribeButton.setBackgroundTintList(getColorStateList(android.R.color.holo_blue_dark))

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

        // PayPal button logic
        paypalLoginButton.setOnClickListener {
            Toast.makeText(this, "Redirecting to PayPal...", Toast.LENGTH_SHORT).show()
            subscribeUser(userId)
        }

        // Subscribe button logic
        subscribeButton.setOnClickListener {
            if (paymentOptions.checkedRadioButtonId == R.id.debit_credit_option) {
                if (validateCreditCardDetails()) {
                    subscribeUser(userId)
                } else {
                    Toast.makeText(this, "Please fill all credit card details.", Toast.LENGTH_SHORT).show()
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
    private fun subscribeUser(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val appDatabase = AppDatabase.getInstance(applicationContext)
            val userDao = appDatabase.userDao()

            val user = userDao.getUserById(userId)

            if (user != null) {
                userDao.setSubscribedStatus(userId, true)
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@SubscribeViewActivity, "Subscription Successful!", Toast.LENGTH_LONG).show()
                finish() // Close activity
            }
        }
    }

}