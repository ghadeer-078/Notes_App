//package com.example.notes_app.DB
//
//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import com.example.notes_app.Model.Note
//
//
//class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "details.db", null, 3) {
//    private val sqLiteDatabase: SQLiteDatabase = writableDatabase
//
//    override fun onCreate(db: SQLiteDatabase?) {
//        db?.execSQL("create table Note (pk INTEGER PRIMARY KEY AUTOINCREMENT,Title Text, Content Text)")
//
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
//        db!!.execSQL("DROP TABLE IF EXISTS Note")
//        onCreate(db)
//    }
//
//
//    //...
//    fun saveData(title: String, content: String) {
//        val contentValues = ContentValues()
//
//        contentValues.put("Title", title)
//        contentValues.put("Content", content)
//
//        sqLiteDatabase.insert("Note", null, contentValues)
//    }
//
//
//    //...
//    fun readData(): ArrayList<Note> {
//        val notes = arrayListOf<Note>()
//
//        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM Note", null)
//        if (cursor.count < 1) {
//            println("No data found!")
//        } else {
//            while (cursor.moveToNext()) {
//                val pk = cursor.getInt(0)
//                val title = cursor.getString(1)
//                val content = cursor.getString(2)
//
//                notes.add(Note(pk, title, content))
//            }
//        }
//
//        return notes
//    }
//
//
//    //...
//    fun updateData(note: Note) {
//        val contentValues = ContentValues()
//
//        contentValues.put("Title", note.title)
//        contentValues.put("Content", note.content)
//
//        sqLiteDatabase.update("Note", contentValues, "pk = ${note.pk}", null)
//    }
//
//    //...
//    fun deleteData(noteID: Int) {
//        sqLiteDatabase.delete("Note", "pk = ?", arrayOf(noteID.toString()))
//
//    }
//
//}