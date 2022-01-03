package com.example.notes_app.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes_app.Model.Note
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlin.concurrent.timer


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Firebase.firestore
    private val notes: MutableLiveData<List<Note>> = MutableLiveData()
    private val TAG = "Firebase"

    //notes
    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun getData() {
        db.collection("Notes")
            // .orderBy("timestamp")
            .get()
            .addOnSuccessListener { result ->
                val allNotes = arrayListOf<Note>()

                for (document in result) {

                    document.data.map { (key, value) ->
                        allNotes.add(
                            Note(
                                document.id,
                                value.toString()
                            )
                        )
                    }
                }
                notes.postValue(allNotes)

            }
            .addOnFailureListener {
                Log.d(TAG, "getNotes: Failed To Get Data")
            }
    }


    fun addNote(note: Note) {
        db.collection("Notes")
            .add(
                hashMapOf(
                    //  "noteTitle" to note.title,
                    "noteContent" to note.content
                    // "timestamp" to FieldValue.serverTimestamp()
                )
            )
            .addOnSuccessListener {
                Log.d(TAG, "addNote: Added Successfully")
                getData()
            }
            .addOnFailureListener {
                Log.d(TAG, "addNote: Failed To Add")
            }
    }


    fun updateNote(id: String, newNote: String) {
        db.collection("Notes")
            //.orderBy("timestamp")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == id) {
                        db.collection("Notes").document(document.id)
                            .update(
                                //"noteTitle", note.title,
                                "noteContent", newNote
                            )
                    }
                }
                getData()

            }
            .addOnFailureListener {
                Log.d(TAG, "updateNote: Failed To Update")
            }
    }


    fun deleteNote(id: String) {
        db.collection("Notes")
            //  .orderBy("timestamp")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == id) {
                        db.collection("Notes").document(document.id)
                            .delete()
                    }
                }
                getData()
            }
            .addOnFailureListener {
                Log.d(TAG, "deleteNote: Failed To Delete")
            }
    }


}