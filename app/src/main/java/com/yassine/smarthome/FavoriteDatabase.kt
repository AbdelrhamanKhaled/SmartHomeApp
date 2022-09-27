package com.yassine.smarthome

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FavoriteDatabase(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(
            "create table favorite_table " +
                    "(favorite text)"
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS favorite_table")
        onCreate(sqLiteDatabase)
    }

    fun insertFavorite(favoriteText: String?) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("favorite", favoriteText)
        db.insert("favorite_table", null, contentValues)
    }

    fun checkIfExist(favorite: String): Boolean {
        val db = this.writableDatabase
        val res = db.rawQuery("select * from favorite_table where favorite = \"$favorite\"", null)
        val ret =  (res.count > 0)
        res?.close()
        return ret
    }

    val allFavorites: ArrayList<String>
        get() {
            val db = this.readableDatabase
            val res = db.rawQuery("select * from favorite_table", null)
            res.moveToFirst()
            val arrayList = ArrayList<String>()
            while (!res.isAfterLast) {
                val index = res.getColumnIndex("favorite")
                arrayList.add(res.getString(index))
                res.moveToNext()
            }
            res.close()
            return arrayList
        }

    fun deleteFromFavorite(favorite: String) {
        val db = this.writableDatabase
        db.execSQL("delete from favorite_table where favorite = \"$favorite\"")
        db.close()
    }

    companion object {
        const val DATABASE_NAME = "favorite.db"
    }
}