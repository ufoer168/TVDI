package com.example.ufoer

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class score : AppCompatActivity() {

    private var s = 0
    private var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score)

        tv = findViewById(R.id.score)
    }

    fun add(v: View?) {
        var id = v?.getId()

        when(id) {
            R.id.btn1 -> {
                s += 1
            }
            R.id.btn2 -> {
                s += 2
            }
            R.id.btn3 -> {
                s += 3
            }
        }

        tv?.setText(s.toString())
    }

}