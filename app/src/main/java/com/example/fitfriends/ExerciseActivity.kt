package com.example.fitfriends

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfriends.databinding.ActivityExerciseBinding
import com.example.sevenminuteworkout.ExerciseStatusAdapter
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() , TextToSpeech.OnInitListener{
    private var binding:ActivityExerciseBinding?=null
    private var restTimer:CountDownTimer?=null
    private var restProgress:Int=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress:Int=0
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition = -1 //invalid index
    private var tts: TextToSpeech?=null
    private var player : MediaPlayer?=null
    private var exerciseAdapter:ExerciseStatusAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar!=null){
           supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
//        binding?.toolbarExercise?.setNavigationOnClickListener {
//        //    customDialogForBackBtn()
//        }
        exerciseList=Constants.defaultExerciseList()
        tts= TextToSpeech(this, this)
//  binding?.flProgressBar?.visibility= View.INVISIBLE
        setupRestView()
        setRecyclerViewExerciseStatus()

    }
    private fun setRecyclerViewExerciseStatus(){
        binding?.recyclerViewExerciseStatus?.layoutManager=LinearLayoutManager(this,
        LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding?.recyclerViewExerciseStatus?.adapter=exerciseAdapter
    }
    private fun setupRestView(){
        if(player!=null){
            player!!.stop()
        }
        try {
            val soundURI= Uri.parse(
                "android.resource://com.example.fitfriends/"+R.raw.rest_view_sound)
            player=MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping=false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }

        binding?.frameLayoutProgressBar?.visibility=View.VISIBLE
        binding?.titleReadyFor?.visibility=View.VISIBLE
        binding?.textViewExercise?.visibility=View.INVISIBLE
        binding?.recyclerViewExerciseStatus?.visibility=View.INVISIBLE
        binding?.imageViewExercise?.visibility=View.INVISIBLE
        binding?.frameLayoutExerciseView?.visibility=View.INVISIBLE
        binding?.textViewUpcomingExercise?.visibility=View.VISIBLE
        binding?.textViewExerciseName?.visibility=View.VISIBLE
        binding?.textViewExerciseName?.text=exerciseList!![currentExercisePosition+1].getName()

        if(restTimer!=null)
        {
            restTimer?.cancel()
            restProgress=0
        }
        setupProgressForRest()
    }

    private fun setupExerciseView(){
        if(player!=null){
            player!!.stop()
        }
        try {
            val soundURI= Uri.parse(
                "android.resource://com.example.fitfriends/"+R.raw.exercise)
            player=MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping=false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
        binding?.frameLayoutProgressBar?.visibility=View.INVISIBLE
       binding?.titleReadyFor?.visibility=View.INVISIBLE
        binding?.textViewExercise?.visibility=View.VISIBLE
        binding?.imageViewExercise?.visibility=View.VISIBLE
        binding?.frameLayoutExerciseView?.visibility=View.VISIBLE
        binding?.recyclerViewExerciseStatus?.visibility=View.VISIBLE

        binding?.textViewUpcomingExercise?.visibility=View.INVISIBLE
        binding?.textViewExerciseName?.visibility=View.INVISIBLE
        if(exerciseTimer!=null)
        {
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())
        binding?.imageViewExercise?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.textViewExercise?.text=exerciseList!![currentExercisePosition].getName()
        setupExerciseProgress()
    }
    private fun setupProgressForRest(){

        binding?.progressBarReady?.progress=restProgress
        restTimer=object:CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBarReady?.progress=10-restProgress
                binding?.textViewTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
            //    Toast.makeText(this@ExerciseActivity, "We'll start the exercise now", Toast.LENGTH_LONG).show()
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()

    }
    private fun setupExerciseProgress() {
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.textViewTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Congratulations!!", Toast.LENGTH_LONG)
                        .show()

                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null)
        {
            restTimer?.cancel()
            restProgress=0
        }
        if(player!=null){
            player!!.stop()
        }
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        if(tts!=null)
        {
            tts?.stop()
            tts?.shutdown()
        }
        binding=null
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            val result=tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
                Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this, "Initialisation failed!", Toast.LENGTH_LONG).show()

        }
    }
    private fun speakOut(text:String){
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, " ")
    }

}