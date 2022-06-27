package com.example.returncup

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

const val DB_VERSION = 3
class DBHelper : SQLiteOpenHelper {
    // test.db파일의 형태로 데이터가 저장된다.
    // 부모생성자 내부에서 데이터베이스를 오픈하고 사용할 수 있는 상태로 만드는 작업을 실행
    constructor(context: Context, s: String, nothing: Nothing?, i: Int):super(context,"third_test.db",null,DB_VERSION)
    // 테이블을 생성하는 쿼리문을 작성한다.
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("test","데이터베이스가 생성되었습니다.")
        val sql = """
            create table third_test(
                phoneNum text primary key,
                userName text,
                userId text,
                userPass text
                )
        """.trimIndent()
        // sql문을 실행
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("test","onUpgrade: $oldVersion -> $newVersion")
        when(oldVersion){
            1 -> {
                // 1 -> 2 버전으로 변경된 경우
            }
            2 -> {
                // 2 -> 3 버전으로 변경된 경우
            }
        }
    }

    fun insert(phoneNum: String, userName: String, userId: String, userPass: String) {
        var db: SQLiteDatabase = writableDatabase
        db.execSQL("INSERT INTO third_test VALUES('" + phoneNum + "'" + ", '" + userName + "'" + ", '" + userId + "'" + ", '" + userPass + "'")
        db.close()
    }

    fun getResult(): String {
        var db: SQLiteDatabase = readableDatabase
        var result: String = ""

        var cursor: Cursor = db.rawQuery("SELECT * FROM third_test", null)
        while (cursor.moveToNext()) {
            result += (cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " : "
                    + cursor.getString(2)
                    + " : "
                    + cursor.getString(3)
                    + "\n")
        }
        return result
    }

    fun getResult1(userId: String, userPass: String): Boolean {
        var db: SQLiteDatabase = readableDatabase
        var result: String = ""

        var cursor: Cursor = db.rawQuery("SELECT userId, userPass FROM third_test", null)
        while (cursor.moveToNext()) {
            result = (cursor.getString(0))
            if (result.equals(userId)) {
                if (cursor.getString(1).equals(userPass)) {
                    return true
                    break
                } else {
                    return false
                }
            }else {

            }
        }

        return false
    }


}