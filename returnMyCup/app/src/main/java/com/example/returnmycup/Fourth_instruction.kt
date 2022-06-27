package com.example.returnmycup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_instruction.*
import kotlinx.android.synthetic.main.activity_start.*

class Fourth_instruction : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_instruction, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(R.drawable.instruction).into(instructionGif)

        btn_ok.setOnClickListener {
            val parentview = activity as MainActivity
            parentview.changeFragment("phoneNum")
        }
        btn_third_home.setOnClickListener {
            var parentview = activity as MainActivity
            parentview.changeFragment("start")
        }

    }

}