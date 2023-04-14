package com.example.fitfriends

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitfriends.databinding.ActivityExerciseBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress: Int = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress: Int = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExerciseIndex = -1 // Invalid index
    private var tts: TextToSpeech? = null
    private var mediaPlayer: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        exerciseList = Constants.ListOfExercises()
        tts = TextToSpeech(this, this)
        setupRestView()
        setRecyclerViewExerciseStatus()

    }

    private fun setRecyclerViewExerciseStatus() {
        // This function is specifically made to show the user the total number of exercises and
        // exercise-number he/she is currently on
        binding?.recyclerViewExerciseStatus?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.recyclerViewExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView() {
        //Setting up the rest view
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
        }
        try {
            val soundURI = Uri.parse(
                "android.resource://com.example.fitfriends/" + R.raw.rest_view_sound
            )
            mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
            mediaPlayer?.isLooping = false
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding?.frameLayoutProgressBar?.visibility = View.VISIBLE
        binding?.titleReadyFor?.visibility = View.VISIBLE
        binding?.textViewExercise?.visibility = View.INVISIBLE
        binding?.recyclerViewExerciseStatus?.visibility = View.INVISIBLE
        binding?.imageViewExercise?.visibility = View.INVISIBLE
        binding?.frameLayoutExerciseView?.visibility = View.INVISIBLE
        binding?.textViewUpcomingExercise?.visibility = View.VISIBLE
        binding?.textViewExerciseName?.visibility = View.VISIBLE
        binding?.textViewExerciseName?.text = exerciseList!![currentExerciseIndex + 1].getName()

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        setupProgressForRest()
    }

    private val tutorialActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                // Check if the tutorial is finished
                val isFinished = data?.getBooleanExtra("IS_FINISHED", false) ?: false
                if (isFinished) {
                    setupExerciseView()
                }
            }
        }

    private fun setUpVideoPlayer() {
        val intent = Intent(this, TutorialActivity::class.java)
        intent.putExtra("INDEX", currentExerciseIndex)
        tutorialActivityResultLauncher.launch(intent)
    }

    private fun setupExerciseView() {
        // Setting up the exercise View
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
        }
        try {
            val soundURI = Uri.parse(
                "android.resource://com.example.fitfriends/" + R.raw.exercise
            )
            mediaPlayer = MediaPlayer.create(applicationContext, soundURI)
            mediaPlayer?.isLooping = false
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding?.frameLayoutProgressBar?.visibility = View.INVISIBLE
        binding?.titleReadyFor?.visibility = View.INVISIBLE
        binding?.textViewExercise?.visibility = View.VISIBLE
        binding?.imageViewExercise?.visibility = View.VISIBLE
        binding?.frameLayoutExerciseView?.visibility = View.VISIBLE
        binding?.recyclerViewExerciseStatus?.visibility = View.VISIBLE

        binding?.textViewUpcomingExercise?.visibility = View.INVISIBLE
        binding?.textViewExerciseName?.visibility = View.INVISIBLE
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        // Speaking out the name of the exercise to be performed
        speakOut(exerciseList!![currentExerciseIndex].getName())
        binding?.imageViewExercise?.setImageResource(exerciseList!![currentExerciseIndex].getImage())
        binding?.textViewExercise?.text = exerciseList!![currentExerciseIndex].getName()
        setupExerciseProgress()
    }

    private fun setupProgressForRest() {
        // Setting up the progress bar of the Rest View : 10 seconds total
        // After every one second the progress bar gets updated
        binding?.progressBarReady?.progress = restProgress
        restTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBarReady?.progress = 10 - restProgress
                // Display the time left inside the circle
                binding?.textViewTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExerciseIndex++
                // Selecting each exercise one by one
                exerciseList!![currentExerciseIndex].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpVideoPlayer()
            }
        }.start()

    }

    private fun setupExerciseProgress() {
        // Setting up the progress bar of the Exercise View : 30 seconds total
        // After every one second, the progress bar gets updated
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                // Display the time left inside the circle
                binding?.textViewTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if (currentExerciseIndex < exerciseList?.size!! - 1) {
                    // De-selecting the selected exercise
                    exerciseList!![currentExerciseIndex].setIsSelected(false)
                    // After it is deselected, setting the IsCompleted attribute to True
                    exerciseList!![currentExerciseIndex].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                } else {
                    // When the exercises are complete, that is, when currentExerciseIndex
                    // exceeds the size of the exerciseList, congratulate the user
                    Toast.makeText(this@ExerciseActivity, "Congratulations!!", Toast.LENGTH_LONG)
                        .show()

                    // The user goes to the Finish Activity then, where an option is given for
                    // playing the game or going back to Home
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        // If the language is not supported or the required data is missing, we let the user know
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Initialisation failed!", Toast.LENGTH_LONG).show()

        }
    }

    private fun speakOut(text: String) {
        // This function is responsible for speaking out the string that is passed to it as an
        // argument
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, " ")
    }

}