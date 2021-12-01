package com.example.fitfriends

import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fitfriends.databinding.ActivityResultBinding

class Result : AppCompatActivity() {
    private var binding: ActivityResultBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        var intent = getIntent()
        var showName = intent.getStringExtra("player_name")
        var showResult = intent.getStringExtra("result")


        binding?.showName?.text = showName
        binding?.showResult?.text = showResult

        binding?.home?.setOnClickListener {
            Toast.makeText(this,"Thank You For Playing", Toast.LENGTH_SHORT).show()
            val intent= Intent(this, GameActivity::class.java).apply{

            }
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}