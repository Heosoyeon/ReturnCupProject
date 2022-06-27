package com.example.returncup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.returncup.LoginActivity

class splashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var threadObj:Thread
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler(Looper.myLooper()!!)

        threadObj = object :Thread(){
            override fun run() {
                super.run()
                //3초 후에 메인 액티비티로 이동
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        handler.postDelayed(threadObj,3000)
    }
}