package com.example.fitfriends

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitfriends.databinding.ActivityTutorialBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.*

/**
 * Display the video tutorial for the exercise
 */
class TutorialActivity : AppCompatActivity() {
    private var binding: ActivityTutorialBinding? = null
    private var exerciseList: ArrayList<ExerciseModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        exerciseList = Constants.ListOfExercises()
        val currIndex = intent.getIntExtra("INDEX", 0)

        val youtubePlayerView = binding?.videoPlayer
        youtubePlayerView?.initialize(object : AbstractYouTubePlayerListener() {
            // Load and play the video
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = exerciseList!![currIndex].getVideoId()
                val startTime = exerciseList!![currIndex].getVideoStartTime()
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, startTime.toFloat())
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            youTubePlayer.pause()
                            backToExerciseActivity()
                        }
                    }, (10000).toLong())
                } else {
                    Toast.makeText(
                        this@TutorialActivity,
                        "Error encountered while playing the video, please try again later!",
                        Toast.LENGTH_LONG
                    ).show()
                    youTubePlayer.pause()
                    backToExerciseActivity()
                }
            }

            // Check if the video has ended
            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                if (state == PlayerConstants.PlayerState.ENDED) {
                    youTubePlayer.pause()
                    backToExerciseActivity()
                }
            }
        })

        // Handle skip tutorial button
        binding?.skipBtn?.setOnClickListener {
            backToExerciseActivity()
        }
    }

    /**
     * Move back to the exercise activity
     * Now it is the kid's turn to perform the exercise
     */
    private fun backToExerciseActivity() {
        val intent = Intent(this@TutorialActivity, ExerciseActivity::class.java)
        intent.putExtra("IS_FINISHED", true)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

