package com.example.notes_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes_app.DB.DatabaseHelper
import com.example.notes_app.Model.Person
import com.example.notes_app.Resource.RVAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etLocation: EditText
    private lateinit var btSave: Button
    private lateinit var btRead: Button
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    private val databaseHelper by lazy { DatabaseHelper(applicationContext) }
    private lateinit var people: ArrayList<Person>


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

        btRead.setOnClickListener {
            people = databaseHelper.readData()
            rvAdapter.update(people)
        }

        loadRV()

    }

    private fun connectView() {
        etName = findViewById(R.id.etName)
        etLocation = findViewById(R.id.etLocation)
        btSave = findViewById(R.id.btSave)
        btRead = findViewById(R.id.btRead)
        rvMain = findViewById(R.id.rvMain)
        people = arrayListOf()
    }


    private fun loadRV() {
        rvAdapter = RVAdapter()
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)
    }


}