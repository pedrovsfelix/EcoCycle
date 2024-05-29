package com.example.ecocycle

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "local.db"
        const val TABLE_NAME = "places"
        const val COLUMN_NAME = "name"
        const val COLUMN_LAT = "lat"
        const val COLUMN_LNG = "lng"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_MATERIAL = "material"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (id INTEGER,$COLUMN_NAME TEXT PRIMARY KEY, $COLUMN_LAT TEXT, $COLUMN_LNG TEXT, $COLUMN_ADDRESS TEXT, $COLUMN_MATERIAL TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertLocal(name: String, lat: String, lng: String, address: String, material: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_LAT, lat)
            put(COLUMN_LNG, lng)
            put(COLUMN_ADDRESS, address)
            put(COLUMN_MATERIAL, material)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }

    fun getAllPlaces(): Cursor {
        val db = readableDatabase
        val places = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        return places
    }
}
