package com.example.returnmycup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.areturn.mqtt.MyMqtt
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : AppCompatActivity() {
    val firstFragment = First_splash()
    val secondFragment = Second_start()
    val thirdFragment = Fourth_instruction()
    val fourthFragment = Third_show()
    val fifthFragment = Fifth_phoneNum()
    val sixthFragment = SIxth_Finish()

    val server_uri = "tcp://192.168.0.127:1883"
    var mymqtt : MyMqtt?= null
    val sub_topic = "brand_result"
    lateinit var fragmentManager:FragmentManager
    var sendata = arrayOf<String>("brandname", "phoneNum")
    lateinit var transaction:FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mymqtt = MyMqtt(this, server_uri)
        mymqtt?.mysetCallback(::onReceived)
        mymqtt?.connect(arrayOf(sub_topic))

        //처음 시작 프레그먼트 설정
        changeFragment("splash")

    }

    fun onReceived(topic:String, message: MqttMessage){
        //토픽의 수신을 처리
        val msg = String(message.payload)
        Log.d("mymqtt", msg)
    }

    fun changeFragment(name:String?){
        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        when(name){
            "splash" -> {
                transaction.replace(R.id.container, firstFragment)
                transaction.addToBackStack("splash") //backstack에서 이름을 구분하기위해
            }
            "start" -> {
                transaction.replace(R.id.container, secondFragment)
                transaction.addToBackStack("start")
            }
            "instruction" -> {
                transaction.replace(R.id.container, thirdFragment)
                transaction.addToBackStack("instruction")
            }
            "show" -> {
                transaction.replace(R.id.container, fourthFragment)
                transaction.addToBackStack("show")
            }
            "phoneNum" -> {
                transaction.replace(R.id.container, fifthFragment)
                transaction.addToBackStack("phoneNum")
            }
            "finish" -> {
                transaction.replace(R.id.container, sixthFragment)
                transaction.addToBackStack("finish")
            }
        }
        transaction.commit()
    }
    fun reload():FragmentManager{
       return fragmentManager
    }
}