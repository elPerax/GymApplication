package com.example.gymapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GymApplication)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExercises = findViewById<Button>(R.id.btnExercises)
        val btnMap = findViewById<Button>(R.id.btnMap)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        btnExercises.setOnClickListener {
            startActivity(Intent(this, ExercisesActivity::class.java))
        }

        btnMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_exercises -> {
                    startActivity(Intent(this, ExercisesActivity::class.java))
                    true
                }
                R.id.nav_map -> {
                    startActivity(Intent(this, MapActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
