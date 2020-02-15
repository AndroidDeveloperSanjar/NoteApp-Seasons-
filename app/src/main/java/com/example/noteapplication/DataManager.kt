package com.example.noteapplication

object DataManager {
    val seasons = HashMap<String, Season>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeSeason()
        initializeNotes()
    }

    private fun initializeSeason() {
        var season = Season("id_autumn", "Kuz fasli")
        seasons[season.id] = season

        season = Season(id = "id_winter", title = "Qish fasli")
        seasons[season.id] = season

        season = Season(title = "Yoz fasli", id = "id_summer")
        seasons[season.id] = season

        season = Season("id_spring", "Bahor fasli")
        seasons[season.id] = season
    }

    private fun initializeNotes() {

        var season = seasons["id_autumn"]!!
        var note = NoteInfo(season, "Bu faslda barglar to'kiladi.",
            "Kuz fasli juda ham yaxshi fasllardan biri hisoblanadi chunki bu fasl yoqimli fasl!")
        notes.add(note)
        note = NoteInfo(season, "Kuzning oxirgi oylarida sovuq tushadi.",
            "Bu faslda hamma yoq sariq rangga burkanadi chunki daraxtlarning bargi sariq rangga kiradi!")
        notes.add(note)

        season = seasons["id_winter"]!!
        note = NoteInfo(season, "Bu faslda qor yog'adi.",
            "Qish fasli sovuq fasli hisoblanadi chunki bu faslda harorat 0 gradusdan ham tushib ketishi mumkin!")
        notes.add(note)
        note = NoteInfo(season, "Bu fasl yoqimsizdir.",
            "Qish fasli ko'pchilikka yoqmaydi chunki sovuq qahraton bo'lgani uchun")
        notes.add(note)

        season = seasons["id_spring"]!!
        note = NoteInfo(season, "Bu fasl tiklanish fasli hisoblandi.",
            "Bu faslda uyquga ketgan o't o'lanu hayvonlar uyqudan turadi va unub o'sadi!")
        notes.add(note)
        note = NoteInfo(season, "Bu fasl yomg'irga boy fasl hisoblanadi.",
            "Bu fasl da hamma ekinlarni ekadi va bu faslda navro'z bayrami bor!")
        notes.add(note)

        season = seasons["id_summer"]!!
        note = NoteInfo(season, "Bu fasl juda issiq hisoblanadi.",
            "Bu faslda harorat 50 gradusdan ham yuqoriga chiqib ketishi mumkin!")
        notes.add(note)
        note = NoteInfo(season, "Bu fasl ko'pchilikka yoqadi.",
            "Bu faslda hamma cho'milishga boradi chunki harorat baland bo'lganligi uchun mazza!")
        notes.add(note)
    }
}