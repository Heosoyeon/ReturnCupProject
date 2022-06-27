package com.example.returnmycup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_show_ur_cup.*
import org.eclipse.paho.client.mqttv3.MqttMessage

class Third_show : Fragment() {
    val sub_topic = "brand_result"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_show_ur_cup, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parentview = activity as MainActivity
        parentview.mymqtt?.mysetCallback(::onReceived)
        parentview.mymqtt?.connect(arrayOf(sub_topic))

        btn_fourth_home.setOnClickListener {
            parentview.changeFragment("start")
        }

    }
    fun onReceived(topic: String, message: MqttMessage){
        //토픽의 수신을 처리
        //EditText에 내용을 출력하기, 영상으로 출력,...도착된 메시지 안에서 온도랑 습도 데이터를 이용해서 차트그래프
        //모션detect가 발생되면 Notification 발생,...
        val msg = String(message.payload)

        Log.d("mymqtt", msg)
        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show()
        val msgdata = msg.split(",")
        mybrand.setText(msgdata[1])
        val parentview = activity as MainActivity
        parentview.sendata[0] = msgdata[1]

        isitright.visibility = View.VISIBLE
        yesorno.visibility = View.VISIBLE


        btn_yes.setOnClickListener {
            parentview.changeFragment("instruction")
        }
        btn_no.setOnClickListener {
           var fragmger =  parentview.reload()
            fragmger.beginTransaction().detach(this).attach(this).commit()
        }

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("kimtest","detach")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("kimtest","onAttach")
    }
}