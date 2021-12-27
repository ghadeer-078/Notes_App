package com.example.notes_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notes_app.DB.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etLocation: EditText
    private lateinit var btSave: Button


    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectView()

        btSave.setOnClickListener {
            val name = etName.text.toString()
            val location = etLocation.text.toString()

            databaseHelper.saveData(name, location)
            Toast.makeText(this, "Save Successes", Toast.LENGTH_LONG).show()

        }
    }

    private fun connectView() {
        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)
        btSave = findViewById(R.id.btSave)
    }
}