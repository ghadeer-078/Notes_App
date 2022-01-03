package com.example.notes_app.Model

import com.google.firebase.Timestamp


data class Note(
    val id: String,
    //val title: String,
    val content: String,
    //val timestamp: Timestamp
) {
    constructor() : this("", "")//Timestamp.now()
}