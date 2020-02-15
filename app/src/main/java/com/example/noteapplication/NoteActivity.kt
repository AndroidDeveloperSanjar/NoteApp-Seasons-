package com.example.noteapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.app_bar_items.*
import kotlinx.android.synthetic.main.content_note.*

class NoteActivity : AppCompatActivity() {

    private var notePosition = POSITION_NOT_SET
    private var isNewNote = false
    private var isCancelling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        setSupportActionBar(toolbar)

        val adapterSeasons = ArrayAdapter<Season>(this,
            android.R.layout.simple_spinner_item,
            DataManager.seasons.values.toList())
        adapterSeasons.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSeasons.adapter = adapterSeasons

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
                intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if(notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            isNewNote = true
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }

        saveButton.setOnClickListener {
            if (textNoteText.text.isNullOrEmpty() ||
                    textNoteTitle.text.isNullOrEmpty()){
                isNewNote = false
                Toast.makeText(
                    this,
                    "Note title is empty or Note text empty!",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                isNewNote = true
                val intent = Intent(this,ItemsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        textNoteTitle.setText(note.title)
        textNoteText.setText(note.text)

        val seasonPosition = DataManager.seasons.values.indexOf(note.season)
        spinnerSeasons.setSelection(seasonPosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cancel -> {
                isCancelling = true
                finish()
                true
            }
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
      //  saveNote()
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu!!.findItem(R.id.action_next)
            if(menuItem != null) {
                menuItem.icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_distrub,theme)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        when {
            isCancelling -> {
                if(isNewNote)
                    DataManager.notes.removeAt(notePosition)
            }
            else -> saveNote()
        }
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = textNoteTitle.text.toString()
        note.text = textNoteText.text.toString()
        note.season = spinnerSeasons.selectedItem as Season
    }
}
