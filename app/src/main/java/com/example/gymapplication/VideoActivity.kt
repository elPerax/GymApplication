package com.example.gymapplication

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val videoFileName = intent.getStringExtra("videoPath")
        val videoNameWithoutExt = videoFileName?.substringBefore(".")
        Log.d("VideoDebug", "Received videoPath = $videoFileName")

        if (!videoNameWithoutExt.isNullOrEmpty()) {
            val videoPath = "android.resource://$packageName/raw/$videoNameWithoutExt"
            val uri = Uri.parse(videoPath)

            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView)
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(uri)

            videoView.setOnPreparedListener {
                videoView.start()
            }

            videoView.setOnErrorListener { _, what, extra ->
                Toast.makeText(this, "Can't play this video.", Toast.LENGTH_LONG).show()
                Log.e("VideoError", "Error playing video. what=$what, extra=$extra")
                true
            }
        } else {
            Toast.makeText(this, "No video found", Toast.LENGTH_SHORT).show()
            Log.e("VideoDebug", "videoPath is null or empty.")
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
