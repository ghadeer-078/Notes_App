package com.example.notes_app.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "details.db", null, 1) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table students (Name Text, Location Text)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }


    fun saveData(name: String, location: String) {
        val contentValues = ContentValues()
        contentValues.put("Name", name)
        contentValues.put("Location", location)

        sqLiteDatabase.insert("students", null, contentValues)
    }
}