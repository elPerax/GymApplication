package com.example.gymapplication

import Exercise
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val exercises: List<Exercise>,
    private val onItemClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.txtName)
        val description: TextView = itemView.findViewById(R.id.txtDescription)
        val category: TextView = itemView.findViewById(R.id.txtCategory)
        val difficulty: TextView = itemView.findViewById(R.id.txtDifficulty)
        val duration: TextView = itemView.findViewById(R.id.txtDuration)
        val equipment: TextView = itemView.findViewById(R.id.txtEquipment)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(exercises[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.name.text = exercise.name
        holder.description.text = exercise.description
        holder.category.text = "Category: ${exercise.category}"
        holder.difficulty.text = "Difficulty: ${exercise.difficulty}"
        holder.duration.text = "Duration: ${exercise.duration} sec"
        holder.equipment.text = "Equipment: ${exercise.equipment}"
    }

    override fun getItemCount(): Int = exercises.size
}
