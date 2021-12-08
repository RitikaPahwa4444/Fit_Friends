package com.example.fitfriends.memory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fitfriends.R

class MemoryGameStart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game_start)
        val memoryGameStart = findViewById<Button>(R.id.memory_game_start)
        memoryGameStart.setOnClickListener {
            val intent = Intent(this, MemoryGame::class.java)
            startActivity(intent)
        }
    }
}