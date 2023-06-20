package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class Scroll : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll)
    }

    fun click_add(v: View) {
        var r2 = Result2("Tom", 100, 99)
        var li: LayoutInflater = LayoutInflater.from(this)
        var v = li.inflate(R.layout.student, null, false)
        var ll: LinearLayout = findViewById(R.id.ll)
        ll.addView(v)

        var tv_name: TextView = v.findViewById(R.id.tv_n)
        var tv_eng: TextView = v.findViewById(R.id.tv_e)
        var tv_math: TextView = v.findViewById(R.id.tv_m)

        tv_name.text = r2.name
        tv_eng.text = r2.eng.toString()
        tv_math.text = r2.math.toString()
    }

    class Result2 {
        var name = ""
        var eng = -1
        var math = -1

        constructor(name: String, eng: Int, math: Int) {
            this.name = name
            this.eng = eng
            this.math = math
        }
    }
}