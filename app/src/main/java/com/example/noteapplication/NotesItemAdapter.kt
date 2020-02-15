package com.example.noteapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesItemAdapter(val context: Context) : RecyclerView.Adapter<NotesItemAdapter.ViewHolder>()
{
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return DataManager.notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = DataManager.notes[position]
        holder.textCourse.text = note.season?.title
        holder.textTitle.text = note.title
        holder.currentPosition = position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCourse = itemView.findViewById(R.id.textCourse) as TextView
        val textTitle = itemView.findViewById(R.id.textTitle) as TextView
        var currentPosition = 0

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra(NOTE_POSITION, currentPosition)
                context.startActivity(intent)
            }
        }
    }
}