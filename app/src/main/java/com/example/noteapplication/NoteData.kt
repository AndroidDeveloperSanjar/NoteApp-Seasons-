package com.example.noteapplication

data class Season (
    val id: String,
    val title: String
) {
    override fun toString(): String {
        return title
    }
}

data class NoteInfo(
    var season: Season? = null,
    var title: String? = null,
    var text: String? = null
)