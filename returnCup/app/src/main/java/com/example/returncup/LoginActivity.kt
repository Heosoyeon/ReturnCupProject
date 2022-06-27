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
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    var poneNumStr : String = ""
    var brandStr : String = ""
    var pointStr : String = ""
    var dateStr : String = ""
//    var datalist = arrayOf<String>(poneNumStr,brandStr,pointStr,dateStr)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener{
            thread{
                // 서버로 전송할 JSONObject를 만들기 - 사용자가 입력한 id와 password를 담고 있음
                var jsonobj = JSONObject()
                jsonobj.put("userId",id.text)
                jsonobj.put("userPass",pwd.text)
                val site = "http://192.168.219.105:8000/android_rest/user_login"
                val url = URL(site)
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
                var result:String? = response.body()?.string()  // string이 아니라 array로

                val jsonArray = JSONTokener(result).nextValue() as JSONArray


                for(i in 0 until jsonArray.length()){
                    var phoneNum = jsonArray.getJSONObject(i).getString("phoneNum")
                    Log.d("http",phoneNum)
                    poneNumStr = phoneNum
                    var acPoint = jsonArray.getJSONObject(i).getString("acPoint")
                    Log.d("http",acPoint)
                    pointStr = acPoint
                    var acBrand = jsonArray.getJSONObject(i).getString("acBrand")
                    Log.d("http",acBrand)
                    brandStr = acBrand
                    var acDate = jsonArray.getJSONObject(i).getString("acDate")
                    Log.d("http",acDate)
                    dateStr = acDate
                    Log.d("http","전화번호 : $poneNumStr, 포인트 : $pointStr, 브랜드 : $brandStr, 날짜 : $dateStr")

                }
//                val myadapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,datalist)
//
//                // 4. ListView에 adapter를 연결
//                listview1.adapter = myadapter
                //listview1.setAdapter(myadaper)    => setter메소드
                //myadapter = listview1.getAdapter  => getter메소드

                result = result?.substring(1,result.length-1)
                if(result != "login fail"){
                    Log.d("http","성공")
                    val intent = Intent(this,MainActivity::class.java).apply {
                        intent.putExtra("poneNumStr", poneNumStr)
                        intent.putExtra("pointStr", pointStr)
                        intent.putExtra("brandStr", brandStr)
                        intent.putExtra("dateStr", dateStr)
//                        intent.putExtra("acBrand", $acBrand)
                    }
                    startActivityForResult(intent,102)
                }

            }
        }
        register.setOnClickListener {
            val intent = Intent(this,registerActivity::class.java).apply {
            }
            startActivityForResult(intent,101)
        }
    }
}

