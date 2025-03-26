package com.example.gymapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExercises = findViewById<Button>(R.id.btnExercises)
        val btnMap = findViewById<Button>(R.id.btnMap)

        btnExercises.setOnClickListener {

            val intent = Intent(this, ExercisesActivity::class.java)
            startActivity(intent)
        }

        btnMap.setOnClickListener {

            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}
