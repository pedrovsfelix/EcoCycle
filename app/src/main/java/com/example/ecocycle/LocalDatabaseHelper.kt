package com.example.ecocycle

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "local.db"
        const val TABLE_NAME = "COOPERATIVAS"
        const val COLUMN_NAME = "NAME"
        const val COLUMN_ENDERECO = "ENDERECO"
        const val COLUMN_TIPO = "TIPO"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ENDERECO TEXT PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_TIPO TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertLocal(name: String, endereco: String, tipo: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_ENDERECO, endereco)
            put(COLUMN_TIPO, tipo)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }
}
