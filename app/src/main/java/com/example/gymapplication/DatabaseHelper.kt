package com.example.gymapplication

import Exercise
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "gymApp.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Exercise"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                description TEXT,
                videoPath TEXT,
                category TEXT,
                difficulty TEXT,
                duration INTEGER,
                equipment TEXT,
                thumbnailPath TEXT
            );
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExercise(exercise: Exercise) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", exercise.name)
            put("description", exercise.description)
            put("videoPath", exercise.videoPath)
            put("category", exercise.category)
            put("difficulty", exercise.difficulty)
            put("duration", exercise.duration)
            put("equipment", exercise.equipment)
            put("thumbnailPath", exercise.thumbnailPath)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllExercises(): List<Exercise> {
        val exercises = mutableListOf<Exercise>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val exercise = Exercise(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    videoPath = cursor.getString(cursor.getColumnIndexOrThrow("videoPath")),
                    category = cursor.getString(cursor.getColumnIndexOrThrow("category")),
                    difficulty = cursor.getString(cursor.getColumnIndexOrThrow("difficulty")),
                    duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration")),
                    equipment = cursor.getString(cursor.getColumnIndexOrThrow("equipment")),
                    thumbnailPath = cursor.getString(cursor.getColumnIndexOrThrow("thumbnailPath"))
                )
                exercises.add(exercise)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return exercises
    }


    fun clearAllExercises() {
        writableDatabase.execSQL("DELETE FROM $TABLE_NAME")
    }
}
