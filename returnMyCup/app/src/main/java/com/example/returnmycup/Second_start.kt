package com.example.returnmycup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_instruction.*
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_start.btn_start

class Second_start : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_start, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_start.setOnClickListener {
            var data:String = ""
            data = "camera_on"
            val parentview = activity as MainActivity
            parentview.mymqtt?.publish("kiosk/camera", data)
            parentview.changeFragment("show")
        }





    }

}