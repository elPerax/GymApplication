package com.example.gymapplication

import Exercise
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ExercisesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbHelper = DatabaseHelper(this)

        // line to clean all duplicate data, once
        // dbHelper.deleteAllExercises()

        dbHelper.insertExercise(
            Exercise(0, "Push-Up", "Upper body strength exercise", "pushup.mp4", "Upper Body", "Beginner", 60, "None", "pushup_thumb")
        )
        dbHelper.insertExercise(
            Exercise(0, "Squat", "Leg strength and balance", "squat.mp4", "Legs", "Beginner", 60, "None", "squat_thumb")
        )
        dbHelper.insertExercise(
            Exercise(0, "Crunches", "Abdominal workout for core", "crunches.mp4", "Core", "Intermediate", 45, "None", "crunches_thumb")
        )
        dbHelper.insertExercise(
            Exercise(0, "Dumbbell Curl", "Bicep isolation using weights", "curl.mp4", "Upper Body", "Intermediate", 30, "Dumbbells", "curl_thumb")
        )

        val exercisesFromDB = dbHelper.getAllExercises()

        adapter = ExerciseAdapter(exercisesFromDB) { selectedExercise: Exercise ->
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("videoPath", selectedExercise.videoPath)
            startActivity(intent)
        }

        recyclerView.adapter = adapter


        val bottomNav = findViewById<BottomNavigationView>(R.id.exercise_bottom_nav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_main -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_map -> {
                    startActivity(Intent(this, MapActivity::class.java))
                    true
                }
                R.id.nav_back -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
