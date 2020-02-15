package com.example.noteapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class CoursesItemAdapter(context: Context) : RecyclerView.Adapter<CoursesItemAdapter.ViewHolder>()
{
    private val layoutInflater = LayoutInflater.from(context)
    private val courses = DataManager.seasons.values.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_course, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return DataManager.seasons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        holder.textCourse.text = course.title
        holder.currentPosition = position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCourse = itemView.findViewById(R.id.textCourse) as TextView
        var currentPosition = 0

        init {
            itemView.setOnClickListener {
                Snackbar.make(it, courses[currentPosition].title,
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }
}