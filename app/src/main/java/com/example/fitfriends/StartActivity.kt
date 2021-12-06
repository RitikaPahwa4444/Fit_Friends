package com.example.fitfriends

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitfriends.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private var player: MediaPlayer?=null
    private var binding:ActivityStartBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val soundURI= Uri.parse(
                "android.resource://com.example.fitfriends/"+R.raw.start_screen)
            player= MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping=false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding= ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.flStart?.setOnClickListener {
            val intent=Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
        if(player!=null){
            player!!.stop()
        }
    }
}