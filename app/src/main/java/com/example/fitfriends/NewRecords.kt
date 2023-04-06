package com.example.fitfriends

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfriends.databinding.ActivityNewRecordsBinding
import kotlinx.coroutines.launch

class NewRecords : AppCompatActivity() {
    private var binding: ActivityNewRecordsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewRecordsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.newRecordsToolbar)

        val actionbar = supportActionBar//actionbar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true) //set back button
            actionbar.title = "NEW RECORDS" // Setting a title in the action bar.
        }

        binding?.newRecordsToolbar?.setNavigationOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dao = (application as FitFriendsApp).db.newRecordsDao()
        getDates(dao)
    }

    private fun getDates(newRecordsDao: NewRecordsDao) {


        lifecycleScope.launch {
            newRecordsDao.getDates().collect { allDatesList ->

                if (allDatesList.isNotEmpty()) {
                    // Here if the List size is greater then 0 we will display the item in the recycler view or else we will show the text view that no data is available.
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.GONE

                    // Creates a vertical Layout Manager
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@NewRecords)

                    // History adapter is initialized and the list is passed in the param.
                    val dates = ArrayList<String>()
                    for (date in allDatesList) {
                        dates.add(date.date)
                    }
                    val historyAdapter = NewRecordsAdapter(ArrayList(dates))

                    binding?.rvHistory?.adapter = historyAdapter
                } else {
                    //      binding?.newRecordsTV?.visibility = View.GONE
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






