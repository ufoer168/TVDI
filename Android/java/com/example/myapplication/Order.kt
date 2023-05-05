package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class Order : AppCompatActivity() {
    lateinit var btn_h_a: Button
    lateinit var btn_h_d: Button
    lateinit var btn_f_a: Button
    lateinit var btn_f_d: Button
    lateinit var btn_reset: Button
    lateinit var num: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order)

        btn_h_a = findViewById(R.id.btn_h_a)
        btn_h_d = findViewById(R.id.btn_h_d)
        btn_f_a = findViewById(R.id.btn_f_a)
        btn_f_d = findViewById(R.id.btn_f_d)
        btn_f_d = findViewById(R.id.btn_reset)

        btn_h_a.setOnClickListener() {
            num = findViewById(R.id.num_h)
            num.setText((num.getText().toString().toInt()+1).toString())
        }

        btn_h_d.setOnClickListener() {
            num = findViewById(R.id.num_h)
            num.setText((num.getText().toString().toInt()-1).toString())
        }

        btn_f_a.setOnClickListener() {
            num = findViewById(R.id.num_f)
            num.setText((num.getText().toString().toInt()+1).toString())
        }

        btn_f_d.setOnClickListener() {
            num = findViewById(R.id.num_f)
            num.setText((num.getText().toString().toInt()-1).toString())
        }

        btn_reset.setOnClickListener() {
            num = findViewById(R.id.num_h)
            num.setText("0")
            num = findViewById(R.id.num_f)
            num.setText("0")
            num = findViewById(R.id.sum)
            num.setText("")
        }
    }

    fun amount() {
        var sum = 0
        num = findViewById(R.id.num_h)
        sum += num.getText().toString().toInt() * 40
        num = findViewById(R.id.num_f)
        sum += num.getText().toString().toInt() * 30
        num = findViewById(R.id.sum)
        num.setText("總計 $"+sum.toString())
    }

}