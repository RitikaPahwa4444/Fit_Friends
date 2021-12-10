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

/*
* ANIMATED SPLASH SCREEN
* This activity uses catchy sounds and animations and appears as soon as the app is opened.
*/
class MainActivity : AppCompatActivity() {
    private var soundPlayer: MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        * SOUND PLAYER 
        * Play sounds as soon as the app is opened
        */
        try {
            val soundURI= Uri.parse(
                "android.resource://com.example.fitfriends/"+R.raw.app_start)
            soundPlayer=MediaPlayer.create(applicationContext, soundURI)
            soundPlayer?.isLooping=false
            soundPlayer?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
        //Remove the notification bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        /*
        * ANIMATIONS
        * Set animations for the activity
        */
        val AnimateTheTop: Animation
        val AnimateTheBottom:Animation
        AnimateTheBottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation)
        AnimateTheTop=AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val image: ImageView =findViewById(R.id.logo)
        image.animation=AnimateTheTop
        val name: TextView =findViewById(R.id.name)
        name.animation=AnimateTheBottom
        //The splash screen is displayed only for 2 seconds
        Handler().postDelayed({
            val intent= Intent(this,StartActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    /*
      * This function stops the sound player so that the sounds are not played once this activity is over.
      */
    override fun onDestroy() {
        super.onDestroy()
        if(soundPlayer!=null){
            soundPlayer!!.stop()
        }
    }
}