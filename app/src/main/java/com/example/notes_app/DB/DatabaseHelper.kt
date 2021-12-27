package com.example.notes_app.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notes_app.Model.Person


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "details.db", null, 2) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table students (pk INTEGER PRIMARY KEY AUTOINCREMENT,Name Text, Location Text)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }


    //...
    fun saveData(name: String, location: String) {
        val contentValues = ContentValues()
        contentValues.put("Name", name)
        contentValues.put("Location", location)

        sqLiteDatabase.insert("students", null, contentValues)
    }


    //...
    fun readData(): ArrayList<Person>{
        val people = arrayListOf<Person>()

        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM students", null)
        if (cursor.count < 1){
            println("No data found!")
        }else{
            while (cursor.moveToNext()){
                val pk = cursor.getInt(0)
                val name = cursor.getString(1)
                val location = cursor.getString(2)

                people.add(Person(pk, name, location))
            }
        }

        return people
    }



}