package com.example.gymapplication

import Exercise
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExercisesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dbHelper = DatabaseHelper(this)

        // Clean old duplicate data, once
        //dbHelper.clearAllExercises()


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

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}
