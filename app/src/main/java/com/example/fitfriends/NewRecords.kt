package com.example.fitfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfriends.databinding.ActivityNewRecordsBinding
import kotlinx.coroutines.launch

class NewRecords : AppCompatActivity() {
    private var binding: ActivityNewRecordsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewRecordsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//        binding?.bmiChecker?.setOnClickListener {
//            val intent= Intent(this, BMIActivity::class.java)
//            startActivity(intent)
//        }
        setSupportActionBar(binding?.toolbarHistoryActivity)

        val actionbar = supportActionBar//actionbar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true) //set back button
            actionbar.title = "HISTORY" // Setting a title in the action bar.
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao = (application as FitFriendsApp).db.newRecordsDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(newRecordsDao: NewRecordsDao) {


        lifecycleScope.launch {
            newRecordsDao.fetchALlDates().collect { allCompletedDatesList ->

                if (allCompletedDatesList.isNotEmpty()) {
                    // Here if the List size is greater then 0 we will display the item in the recycler view or else we will show the text view that no data is available.
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.GONE

                    // Creates a vertical Layout Manager
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@NewRecords)

                    // History adapter is initialized and the list is passed in the param.
                    val dates = ArrayList<String>()
                    for (date in allCompletedDatesList) {
                        dates.add(date.date)
                    }
                    val historyAdapter = NewRecordsAdapter(ArrayList(dates))

                    // Access the RecyclerView Adapter and load the data into it
                    binding?.rvHistory?.adapter = historyAdapter
                } else {
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                }

            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}






