package com.example.returncup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
//    var i = 0
//    var pointString = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener{
            thread{
                // 서버로 전송할 JSONObject를 만들기 - 사용자가 입력한 id와 password를 담고 있음
                var jsonobj = JSONObject()
                jsonobj.put("userId",id.text)
                jsonobj.put("userPass",pwd.text)
                val url = "http://192.168.219.105:8000/android_rest/user_login"

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
                //var result:String? = response.body()?.string()  // string이 아니라 array로

//                var result = jsonobj.optJSONArray("pointinfo")
                var result = response.arrayOf<String>()

                 Log.d("http",result!!)
/////////////////////
                val jsonObject = JSONTokener(result).nextValue() as JSONObject

                val phonenum = jsonObject.getString("phoneNum")
                Log.d("전화번호: ", phonenum)

//                val username = jsonObject.getString("userName")
//                Log.i("이름: ", username)

                val brand = jsonObject.getString("acBrand")
                Log.d("브랜드: ", brand)

                val pointDate = jsonObject.getString("acDate")
                Log.d("날짜: ", pointDate)
/////////////////////

                // 로그인성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드
//                result = result.substring(1,result.length-1)

//                for(i in 0..result.length()){
//                    var jsonObject = result.getJSONObject(i)
//
////                    var name = jsonObject.getString("userName")
////                    var point = jsonObject.getString("acPoint")
////                    var brand = jsonObject.getString("acBrand")
////                    var point_date = jsonObject.getString("acDate")
//
////                    pointString += "이름 : $name, 포인트 : $point, 브랜드 : $brand, 날짜 : $point_date"
//                    Log.d("http",jsonObject)
//
//                }

//                if(result == ){
//                    Log.d("http","성공")
////                    Log.d("http",result)
//                    val intent = Intent(this,MainActivity::class.java).apply {
//                    }
//                    startActivityForResult(intent,102)
//                }
//                val intent = Intent(this,MainActivity::class.java).apply {
//                }
//                startActivityForResult(intent,102)
            }
        }
        register.setOnClickListener {
            val intent = Intent(this,registerActivity::class.java).apply {
            }
            startActivityForResult(intent,101)
        }
    }
}

