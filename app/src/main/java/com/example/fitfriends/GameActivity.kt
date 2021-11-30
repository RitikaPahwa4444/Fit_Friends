package com.example.fitfriends

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.fitfriends.databinding.ActivityExerciseBinding
import com.example.fitfriends.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private var binding: ActivityGameBinding? = null

    var level = ""
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        sharedPreferences = this.getSharedPreferences("time", Context.MODE_PRIVATE)
        binding?.bestTime?.text = sharedPreferences.getString("Best", "00:00")
        val ltime: String? = sharedPreferences.getString("Last", "00:00")

        binding?.lastGameTime?.text = ltime

        //User Clicking on the Custom button
        binding?.custombutton?.setOnClickListener {
            val intent = Intent(this@GameActivity, CustomBoard::class.java).apply {
                putExtra("height", 9)
                putExtra("width", 9)
                putExtra("mines", 40)
            }
            startActivity(intent)
        }
        binding?.easy?.setOnClickListener {
            level = "easy"
        }
        binding?.medium?.setOnClickListener {
            level = "medium"
        }
        binding?.hard?.setOnClickListener {
            level = "hard"
        }

        binding?.start?.setOnClickListener {
            if(level==""){
                Toast.makeText(this, "Choose Valid Option", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GamePlay::class.java).apply {
                    putExtra("selectedLevel", level)
                    putExtra("flag", 1)
                }
                startActivity(intent)
            }
        }

        //User clicking on the Rules Button
        binding?.rules?.setOnClickListener{
            showInstructions()
        }


    }
    // Display instructions to the player
    private fun showInstructions() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("RULES")
        builder.setMessage("The purpose of the game is to reveal all the cells of the board which do not contain a bomb. You lose if you set off a mine cell.\n" +
                "\n" +
                "Every non-mine cell you open will tell you the total number of bombs located in the eight neighboring cells. Once you are sure that a cell contains a bomb, you can click on the bomb button and then mark the position of bomb by a flag. Once you have flagged all the bombs around an open cell, you can quickly open the remaining non-bomb cells by shift-clicking on the cell.\n" +
                "\n" +
                "To start a new game (abandoning the current one), click on the refresh button.\n" +
                "\n" +
                "Happy mine hunting!")

        builder.setCancelable(false)

        builder.setPositiveButton("OK"
        ){ dialog, which ->

        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
