package com.example.gymapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class VideoActivity : AppCompatActivity() {

    private val CHANNEL_ID = "exercise_complete_channel"
    private val NOTIFICATION_ID = 1001
    private val REQUEST_CODE_NOTIFICATIONS = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val videoName = intent.getStringExtra("videoPath")?.substringBefore(".")
        val videoPath = "android.resource://${packageName}/raw/$videoName"
        val uri = Uri.parse(videoPath)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE_NOTIFICATIONS
            )
        }

        videoView.setVideoURI(uri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        createNotificationChannel()

        videoView.setOnPreparedListener {
            videoView.start()
        }

        videoView.setOnCompletionListener {
            sendNotification(videoName ?: "an exercise")
        }

        videoView.setOnErrorListener { _, _, _ ->
            Toast.makeText(this, "Can't play this video.", Toast.LENGTH_SHORT).show()
            true
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun sendNotification(exerciseName: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Workout tutorial Complete 💪")
            .setContentText("You just completed watching: $exerciseName!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Exercise Notifications"
            val descriptionText = "Notifications after completing an exercise"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
