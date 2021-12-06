package com.example.fitfriends

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.fitfriends.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var player: MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            val soundURI= Uri.parse(
                "android.resource://com.example.fitfriends/"+R.raw.app_start)
            player=MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping=false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val topAnim: Animation
        val botAnim:Animation
        botAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation)
        topAnim=AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val image: ImageView =findViewById(R.id.logo)
        image.animation=topAnim
        val name: TextView =findViewById(R.id.name)
        name.animation=botAnim
        Handler().postDelayed({
            val intent= Intent(this,StartActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }

    override fun onDestroy() {
        super.onDestroy()
        if(player!=null){
            player!!.stop()
        }
    }
}