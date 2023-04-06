package com.example.fitfriends


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.fitfriends.databinding.ActivityFinishBinding
import com.example.fitfriends.memory.MemoryGameStart
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * This activity marks the end of the exercises and allows the user to play the game
 * At the same time, the date and time are added to the database
 **/
class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnFinish?.setOnClickListener {
            val intent = Intent(this, MemoryGameStart::class.java)
            startActivity(intent)
            finish()
        }
        binding?.btnSkip?.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }
        // get the dao through the database in the application class
        val dao = (application as FitFriendsApp).db.newRecordsDao()
        addDate(dao)
    }


    private fun addDate(newRecordsDao: NewRecordsDao) {

        val calendar = Calendar.getInstance() // Calendars Current Instance
        val dateTime = calendar.time // Current Date and Time of the system.
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