package com.example.returnmycup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class SIxth_Finish : Fragment() {
    lateinit var handler: Handler
    lateinit var threadObj:Thread
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_finish, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.myLooper()!!)

        threadObj = object :Thread(){
            override fun run() {
                super.run()
                //3초 후에 메인 액티비티로 이동
                val parentview = activity as MainActivity
                parentview.changeFragment("start")
            }
        }
        handler.postDelayed(threadObj,10000)

    }
}