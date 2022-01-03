package com.example.notes_app

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.Model.Note
import com.example.notes_app.Resource.RVAdapter
import com.example.notes_app.ViewModel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp


class MainActivity : AppCompatActivity() {

    lateinit var llMain: LinearLayout

    // private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var btSave: FloatingActionButton

    private lateinit var rvMain: RecyclerView

    //Firebase...
    private val mainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    ///local database...
//    val databaseHelper by lazy { DatabaseHelper(applicationContext) }
    private lateinit var notes: ArrayList<Note>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectView()
//        readData()

        mainViewModel.getNotes().observe(this) {
            updtRC(it)
        }
        mainViewModel.getData()


        btSave.setOnClickListener {
            // val title = etTitle.text.toString()
            val content = etContent.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {

                mainViewModel.addNote(
                    Note(
                        "",
                        // etTitle.text.toString(),
                        etContent.text.toString()
                        // Timestamp.now()
                    )
                )

//                ///local database...
//                databaseHelper.saveData(title, content)

                //Toast.makeText(this, "Save Successes", Toast.LENGTH_LONG).show()
                Snackbar.make(llMain, "Save Successes", Snackbar.LENGTH_LONG).show()
            } else {
                //Toast.makeText(this, "Save Successes", Toast.LENGTH_LONG).show()
                Snackbar.make(llMain, "Please Enter something!", Snackbar.LENGTH_LONG).show()
            }

            clearText()
            //readData()
        }

        //loadRV()
    }

    private fun connectView() {
        llMain = findViewById(R.id.llMain)
        //etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        btSave = findViewById(R.id.btSave)
        rvMain = findViewById(R.id.rvMain)
        notes = arrayListOf()
    }


    fun updtRC(lsNote: List<Note>) {
        rvMain.adapter = RVAdapter(lsNote, this)
        rvMain.layoutManager = LinearLayoutManager(this)
    }

    private fun clearText() {
        //   etTitle.text.clear()
        etContent.text.clear()
        //   etTitle.clearFocus()
        etContent.clearFocus()
    }

//    ///local database...
//    private fun readData() {
//        notes = databaseHelper.readData()
//        loadRV()
//    }
//    //local database...
//    private fun loadRV() {
//        rvAdapter = RVAdapter(notes, this)
//        rvMain.adapter = rvAdapter
//        rvMain.layoutManager = LinearLayoutManager(this)
//    }
//    private fun editNote(noteID: Int, noteTitle: String, noteContent: String) {
//        ///local Database...
//        databaseHelper.updateData(Note(noteID, noteTitle, noteContent))
//        readData()
//    }


    fun delete(note: Note) {
        mainViewModel.deleteNote(note.id)
//        ///local database...
//        databaseHelper.deleteData(pk)
//        readData()
    }

    fun raiseDialog(note: Note) {
        //var bodyOfDialog = LinearLayout(this)
        val dialogBuilder = AlertDialog.Builder(this)

        //val updatedTitle = EditText(this)
        val updatedContent = EditText(this)
        // updatedTitle.hint = "Enter new Title..."
        updatedContent.hint = "Enter new Text..."

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                //updatedTitle.text.isNotEmpty() &&
                if (updatedContent.text.isNotEmpty()) {
                    mainViewModel.updateNote(note.id, updatedContent.text.toString())

                } else {
                    Toast.makeText(this, "Please Enter Something!", Toast.LENGTH_LONG).show()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")

        // bodyOfDialog.orientation
        //bodyOfDialog.addView(updatedTitle)
        //bodyOfDialog.addView(updatedContent)

        alert.setView(updatedContent)
        alert.show()
    }

}