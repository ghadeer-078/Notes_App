package com.example.notes_app

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.DB.DatabaseHelper
import com.example.notes_app.Model.Person
import com.example.notes_app.Resource.RVAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var llMain: LinearLayout
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var btSave: FloatingActionButton

    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    val databaseHelper by lazy { DatabaseHelper(applicationContext) }
    private lateinit var notes: ArrayList<Person>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectView()
        readData()

        btSave.setOnClickListener {
            val title = etTitle.text.toString()
            val content = etContent.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                databaseHelper.saveData(title, content)
                //Toast.makeText(this, "Save Successes", Toast.LENGTH_LONG).show()
                Snackbar.make(llMain, "Save Successes", Snackbar.LENGTH_LONG).show()
            } else {
                //Toast.makeText(this, "Save Successes", Toast.LENGTH_LONG).show()
                Snackbar.make(llMain, "Please Enter something!", Snackbar.LENGTH_LONG).show()
            }

            clearText()
            readData()
        }

        loadRV()
    }

    private fun connectView() {
        llMain = findViewById(R.id.llMain)
        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        btSave = findViewById(R.id.btSave)
        rvMain = findViewById(R.id.rvMain)
        notes = arrayListOf()
    }

    fun readData() {
        notes = databaseHelper.readData()
        loadRV()
    }

    fun loadRV() {
        rvAdapter = RVAdapter(notes, this)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)
    }


    private fun editNote(noteID: Int, noteTitle: String, noteContent: String) {
        databaseHelper.updateData(Person(noteID, noteTitle, noteContent))
        readData()
    }


    fun clearText() {
        etTitle.text.clear()
        etContent.text.clear()
        etTitle.clearFocus()
        etContent.clearFocus()
    }

    fun delete(pk: Int) {
        databaseHelper.deleteData(pk)
        readData()
    }

    fun raiseDialog(id: Int) {
        val dialogBuilder = AlertDialog.Builder(this)

        val updatedTitle = EditText(this)
        //updatedTitle.setText()

        val updatedContent = EditText(this)
        updatedContent.hint = "Enter new Text..."

        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                editNote(id, updatedTitle.text.toString(), updatedContent.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedTitle)
        alert.setView(updatedContent)

        alert.show()
    }

}