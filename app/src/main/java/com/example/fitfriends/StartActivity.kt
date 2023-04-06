package com.example.fitfriends

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitfriends.databinding.ActivityStartBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StartActivity : AppCompatActivity() {
    private var soundPlayer: MediaPlayer? = null
    private var binding: ActivityStartBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * SOUND PLAYER
         * Plays a sound in the background
         **/
        try {
            val soundURI = Uri.parse(
                "android.resource://com.example.fitfriends/" + R.raw.start_screen
            )
            soundPlayer = MediaPlayer.create(applicationContext, soundURI)
            soundPlayer?.isLooping = false
            soundPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        /**
         *Change activity when the start frame layout is tapped
         **/
        binding?.StartFL?.setOnClickListener {

            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
            finish()
        }
        /**
         * Display records set by the kid
         **/
        binding?.NewRecordsBtn?.setOnClickListener {
            val intent = Intent(this, NewRecords::class.java)
            startActivity(intent)
            finish()
        }
        /**
         * Quit the application when Quit button is pressed
         **/
        binding?.quit?.setOnClickListener {
            finish()
        }
        binding?.aboutBtn?.setOnClickListener {
            setContentView(R.layout.about_screen)
            val back: Button = findViewById(R.id.backBtn)
            back.setOnClickListener {
                setContentView(binding?.root)
            }
        }
    }


    /**
     * This function sets binding to null to prevent memory leakage and stop the background sound
     **/

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        if (soundPlayer != null) {
            soundPlayer!!.stop()
        }
    }
}