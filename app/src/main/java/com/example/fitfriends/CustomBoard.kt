package com.example.fitfriends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.widget.EditText
import com.example.fitfriends.databinding.ActivityCustomBoardBinding
import com.example.fitfriends.databinding.ActivityGameBinding

//This activity helps in passing the input values of the height, width, and number of mines to the GamePlay activity(which sets up the board according to the input values)
class CustomBoard : AppCompatActivity() {
    private var binding: ActivityCustomBoardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomBoardBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        binding?.submit?.setOnClickListener {
            var height = Integer.parseInt(binding?.hi?.text.toString())
            var width = Integer.parseInt(binding?.wi?.text.toString())
            var mine = Integer.parseInt(binding?.mi?.text.toString())

            val intent = Intent(this, GamePlay::class.java).apply {
                putExtra("height", height)
                putExtra("width", width)
                putExtra("mines", mine)
            }
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}