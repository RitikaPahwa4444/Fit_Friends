package com.example.fitfriends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity_height)
        val ht: TextInputEditText = findViewById(R.id.height)
        if (ht.toString().isNotEmpty()) {
            val btn: Button = findViewById(R.id.nextBtn)
            btn.setOnClickListener {
                setContentView(R.layout.activity_bmiactivity_weight)
                val wt: TextInputEditText = findViewById(R.id.weight)
                if (wt.toString().isNotEmpty()) {
                    val calc: Button = findViewById(R.id.calculate)
                    calc.setOnClickListener {
                        setContentView(R.layout.activity_bmiactivity)
                        val bmi: TextView = findViewById(R.id.bmi)
                        val ans = wt.toString().toBigDecimal() / (ht.toString()
                            .toBigDecimal() * ht.toString().toBigDecimal())
                        bmi.text = ans.toString()
                        val dao = (application as FitFriendsApp).db.newRecordsDao()
                        addBMIToDatabase(dao)
//                        when(bmi){
//
//                        }
                    }
                } else {
                    Toast.makeText(this, "Enter valid input", Toast.LENGTH_LONG).show()

                }

            }
        } else {
            Toast.makeText(this, "Enter valid input", Toast.LENGTH_LONG).show()
        }
    }
        private fun addBMIToDatabase(newRecordsDao: NewRecordsDao) {

            val c = Calendar.getInstance() // Calendars Current Instance
            val dateTime = c.time // Current Date and Time of the system.
            Log.e("Date : ", "" + dateTime) // Printed in the log.

            /**
             * Here we have taken an instance of Date Formatter as it will format our
             * selected date in the format which we pass it as an parameter and Locale.
             * Here I have passed the format as dd MMM yyyy HH:mm:ss.
             *
             * The Locale : Gets the current value of the default locale for this instance
             * of the Java Virtual Machine.
             */
            val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // Date Formatter
            val date = sdf.format(dateTime) // dateTime is formatted in the given format.
            Log.e("Formatted Date : ", "" + date) // Formatted date is printed in the log.

            lifecycleScope.launch {
                newRecordsDao.insert(NewRecordsEntity(date)) // Add date function is called.
                Log.e(
                    "Date : ",
                    "Added..."
                ) // Printed in log which is printed if the complete execution is done.
            }
        }


}