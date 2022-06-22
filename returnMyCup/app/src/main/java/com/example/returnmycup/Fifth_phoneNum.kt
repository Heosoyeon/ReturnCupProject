package com.example.returnmycup

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_enter_num.*
import kotlinx.android.synthetic.main.activity_instruction.*
import okhttp3.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class Fifth_phoneNum : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_enter_num, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_confirm.setOnClickListener {
            mynum_check.text = enter_num.text
            val myNum = mynum_check.text.toString()
            val parentview = activity as MainActivity
            //parentview.sendata[1] = mynum_check.text.toString()
            parentview.sendata[1] = myNum
            //Toast.makeText(getActivity(), "$mydata, $mydata2", Toast.LENGTH_LONG).show()
            Toast.makeText(parentview, "$myNum", Toast.LENGTH_LONG).show()

            //http요청
            thread {
                var jsonobj = JSONObject()
                jsonobj.put("phoneNum", myNum)
                jsonobj.put("acBrand", parentview.sendata[0])

                val url = "http://192.168.0.127:8000/restAPI/login"

                val client = OkHttpClient()
                //json데이터를 이용해서 request를 저장
                val jsondata = jsonobj.toString()
                //서버에 요청을 담당하는 객체
                val builder = Request.Builder()
                builder.url(url)
                //요청메시지 만들고 요청메시지의 타입이 json이라고 설정
                builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata))
                val myrequest:Request = builder.build()
                //생성한 request객체를 이용해서 웹에 request하기
                //-request결과로 response객체가 리턴
                val response: Response = client.newCall(myrequest).execute()
                //response에서 메시지 꺼내서 로그 출력하기
                val result:String? = response.body()?.string()

                getActivity()?.runOnUiThread {
                    myname_check.text = result

                    btn_confirm2.setOnClickListener {
                        thread {
                            val url = "http://192.168.0.127:8000/restAPI/insert"
                            builder.url(url)
                            builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata))
                            val myrequest:Request = builder.build()
                            val response: Response = client.newCall(myrequest).execute()
                            val result:String? = response.body()?.string()
                        }
                        parentview.changeFragment("finish")
                    }
                }

            }






            enter_num.setText("")
            var service = getActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(enter_num.windowToken, 0)
            enter_num.clearFocus()

        }
        /*btn_confirm2.setOnClickListener {
            val parentview = activity as MainActivity
            //http통신으로 db에 저장한 data들 ******************************************* 아직 구현안함함
            val mydata = parentview.sendata[0]
            val mydata2 = parentview.sendata[1]

            parentview.changeFragment("finish")
        }*/
        btn_fifth_home.setOnClickListener {
            var parentview = activity as MainActivity
            parentview.changeFragment("start")
        }

    }

}