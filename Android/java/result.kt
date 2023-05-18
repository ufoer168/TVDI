package com.example.ufoer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class result : AppCompatActivity() {

    lateinit var img: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result)

        img = findViewById(R.id.img)
    }

    fun btn1(v: View) {
        startActivityForResult(Intent(this, result1::class.java), 1)
    }

    fun btn2(v: View) {
        startActivityForResult(Intent(this, result2::class.java), 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            when(requestCode) {
                1 -> {
                    var r1 = data?.getSerializableExtra("IMG") as Int
                    img.setImageResource(r1)
                }

                2 -> {
                    var r2 = data?.getSerializableExtra("DATA") as result2_
                    var tv_name: TextView = findViewById(R.id.name)
                    var tv_eng: TextView = findViewById(R.id.eng)
                    var tv_math: TextView = findViewById(R.id.math)

                    tv_name.text = r2.name
                    tv_eng.text = "英文 " + r2.eng + " 分"
                    tv_math.text = "數學 " + r2.math + " 分"
                }
            }
        }
    }

}