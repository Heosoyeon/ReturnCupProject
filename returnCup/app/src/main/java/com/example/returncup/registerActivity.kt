package com.example.returncup

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread

class registerActivity : AppCompatActivity() {
    var mydb : SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton.setOnClickListener {
            thread {
                //서버로 전송할 JSONObject를 만들기 - 사용자가 입력한 id와 password를 담고 있음
                var jsonobj = JSONObject()
                jsonobj.put("phoneNum",register_num.text)
                jsonobj.put("userName",register_name.text)
                jsonobj.put("userId",register_id.text)
                jsonobj.put("userPass",register_pwd.text)
                val url = "http://192.168.219.105:8000/android_rest/register"

                //okhttp3라이브러리의 OkHttpClient객체를 이ipc용해서 작업
                val client = OkHttpClient()

                //json데이터를 이용해서 request처리
                val jsondata = jsonobj.toString()
                //서버에 요청을 담당하는 객체
                val builder = Request.Builder() //request객체를 만들어주는 객체생성
                builder.url(url)//Builder객체에 request할 주소(네트워크상의 주소) 셋팅
                builder.post(
                    RequestBody.create(
                        MediaType.parse("application/json"),
                        jsondata
                    )
                )//요청메시지만들고 요청메시지의
                //타입이 json이라고 설정
                val myrequest: Request = builder.build()//Builder객체를 이용해서 request객체만들기
                //생성한 request객체를 이용해서 웹에 request하기 - request결과로 response객체가 리턴
                val response: Response = client.newCall(myrequest).execute()

                //response에서 메시지 꺼내서 로그출력하기
                val result: String? = response.body()?.string()  // ?으로 null 체크
                Log.d("http", result!!)
                //로그인성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드

            }
            Toast.makeText(applicationContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,LoginActivity::class.java).apply {
            }
            startActivityForResult(intent,101)
        }
    }
}