package com.example.fitfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fitfriends.R
import com.example.fitfriends.databinding.ActivityResultsBinding
//This activity is responsible for getting the result and displaying it
class results : AppCompatActivity() {
    private var binding: ActivityResultsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        var intent = getIntent()
        var showName = intent.getStringExtra("player_name")
        var showResult = intent.getStringExtra("result")


        binding?.showName?.text = showName
        binding?.showResult?.text = showResult

        binding?.home?.setOnClickListener {
            Toast.makeText(this,"Thank You For Playing", Toast.LENGTH_SHORT).show()
            val intent= Intent(this, MainActivity::class.java).apply{

            }
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}