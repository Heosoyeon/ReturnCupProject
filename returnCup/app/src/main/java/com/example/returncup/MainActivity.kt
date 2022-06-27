package com.example.returncup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

//    var userPointList = arrayOf<String>("날짜(2022-06-01 10:00:00)","브랜드명","포인트")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = intent  // FirstActivity로부터 인텐트 받아와서 그것을 intent라는 변수에 저장
//        var datalist = intent.getStringArrayExtra("datalist")
        var poneNumStr = intent.getStringArrayExtra("poneNumStr")
        var pointStr = intent.getStringArrayExtra("pointStr")
        var brandStr = intent.getStringArrayExtra("brandStr")
        var dateStr = intent.getStringArrayExtra("dateStr")
        Log.d("http","넘어왔니? : $poneNumStr, $pointStr, $brandStr, $dateStr")
//        Log.d("http","넘어왔니? : $datalist")

        username.text = "$poneNumStr 님의 적립금"
        pointAmass.text = "$pointStr  P"

        var datalist = arrayOf<String>("포인트 금액 : $pointStr   브랜드명 : $brandStr   적립일 : $dateStr")
        val myadapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,datalist)

        // 4. ListView에 adapter를 연결
        listview1.adapter = myadapter
    }
}
