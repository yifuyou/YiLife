/*
 * Copyright (c) 2023.  edit by Yifuyou
 */

package com.yifuyou.main.db

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yifuyou.main.pojo.PwdSaveItem
import java.lang.ref.WeakReference


/**
 * 密码数据库管理类
 *
 * @author  zjf
 * @since 2023/12/11
 */
object DataUtil {
    private const val DATABASE_NAME: String = "Ylife_1.db"

    private const val TABLE_NAME: String = "pwd_list"

    private lateinit var dbHelper: MyHelper

    fun init(context: Application) {
        dbHelper = MyHelper(context, null, 1)
    }

    fun insert(item: PwdSaveItem) {
        dbHelper.writableDatabase.insert(TABLE_NAME, null,  ContentValues().apply {
            put("userId", item.userId)
            put("info", item.info)
            put("pwd", item.pwd)
        })
    }

    fun update(item: PwdSaveItem) {
        dbHelper.writableDatabase.update(TABLE_NAME, ContentValues().apply {
            put("userId", item.userId)
            put("info", item.info)
            put("pwd", item.pwd)
        }, " itemId = ${item.itemId}", null)
    }

    fun delete(itemId: Int) {
        dbHelper.writableDatabase.delete(TABLE_NAME, "itemId = ${itemId}", null)
    }

    fun getAll() : ArrayList<PwdSaveItem>{
        val list = ArrayList<PwdSaveItem>()
        dbHelper.readableDatabase.rawQuery("select * from $TABLE_NAME", null).let { cursor ->
            if (!cursor.moveToFirst()) {
                cursor.close()
                return list
            }
            with(cursor) {
                do {
                    val pwdSaveItem = PwdSaveItem()
                    pwdSaveItem.itemId = getInt(0)
                    pwdSaveItem.userId = getString(1)
                    pwdSaveItem.info = getString(2)
                    pwdSaveItem.pwd = getString(3)
                    list.add(pwdSaveItem)
                } while (moveToNext())
            }
            cursor.close()
        }
        return list
    }

    class MyHelper(var context: Context, factory: SQLiteDatabase.CursorFactory?, var version: Int) : SQLiteOpenHelper(context, DATABASE_NAME, factory, version) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE $TABLE_NAME(itemId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + " userId VARCHAR(120),"
                    + " info VARCHAR(120),"
                    + " pwd VARCHAR(10000))")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }

    }
}