package com.example.returncup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

//    var userPointList = arrayOf<String>("날짜(2022-06-01 10:00:00)","브랜드명","포인트")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        thread{
            // 서버로 전송할 JSONObject를 만들기 - 사용자가 입력한 id와 password를 담고 있음
            var jsonobj = JSONObject()
            jsonobj.put("userName",username.text)
            jsonobj.put("acPoint",pointAmass.text)
            jsonobj.put("acBrand",username.text)
            jsonobj.put("acDate",username.text)

            var userPointList = arrayOf<String>("acDate","acBrand","acPoint")

//            jsonobj.put("userPass",pwd.text)
            val url = "http://192.168.219.105:8000/android_rest/list"
            // 1. ListView의 출력할 데이터

            // okhttp3 라이브러리의 OkHttpClient객체를 이용해서 작업
            val client = OkHttpClient()

            // json데이터를 이용해서 request처리
            val jsondata = jsonobj.toString()
            // 서버에 요청을 담당하는 객체
            val builder = Request.Builder()  // request객체를 만들어주는 객체 생성
            builder.url(url)  // Builder객체에 request할 주소(=네트워크 상의 주소) 셋팅
            builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata))  // 요청 메시지 만들고, 요청메시지의
            // 타입이 json이라고 설정
            val myrequest: Request = builder.build()  // Builder객체를 이용해서 request객체 만들기
            // 생성한 request객체를 이용해서 웹에 request하기 - request결과로 response객체가 리턴
            val response: Response = client.newCall(myrequest).execute()

            // response에서 메시지 꺼내서 로그출력하기
            val result:String? = response.body()?.string()

            val myadapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,userPointList)
        }
    }
}


//        val intent = intent  // FirstActivity로부터 인텐트 받아와서 그것을 intent라는 변수에 저장
//        val name = intent.getStringArrayExtra("name")
//
//        username.text = "$userName 님의 적립금"
//        pointAmass.text = "$acPoint P"