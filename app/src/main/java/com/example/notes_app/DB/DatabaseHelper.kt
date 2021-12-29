package com.example.notes_app.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notes_app.Model.Person


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "details.db", null, 3) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Note (pk INTEGER PRIMARY KEY AUTOINCREMENT,Title Text, Content Text)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS Note")
        onCreate(db)
    }


    //...
    fun saveData(title: String, content: String) {
        val contentValues = ContentValues()

        contentValues.put("Title", title)
        contentValues.put("Content", content)

        sqLiteDatabase.insert("Note", null, contentValues)
    }


    //...
    fun readData(): ArrayList<Person> {
        val notes = arrayListOf<Person>()

        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM Note", null)
        if (cursor.count < 1) {
            println("No data found!")
        } else {
            while (cursor.moveToNext()) {
                val pk = cursor.getInt(0)
                val title = cursor.getString(1)
                val content = cursor.getString(2)

                notes.add(Person(pk, title, content))
            }
        }

        return notes
    }


    //...
    fun updateData(person: Person) {
        val contentValues = ContentValues()

        contentValues.put("Title", person.title)
        contentValues.put("Content", person.content)

        sqLiteDatabase.update("Note", contentValues, "pk = ${person.pk}", null)
    }

    //...
    fun deleteData(noteID: Int) {
        sqLiteDatabase.delete("Note", "pk = ?", arrayOf(noteID.toString()))

    }

}