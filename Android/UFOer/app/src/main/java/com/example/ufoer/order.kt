package com.example.ufoer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class order : AppCompatActivity() {

    lateinit var btn_h_a: Button
    lateinit var btn_h_d: Button
    lateinit var btn_f_a: Button
    lateinit var btn_f_d: Button
    lateinit var btn_reset: Button
    lateinit var tv: TextView
    var num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order)

        btn_h_a = findViewById(R.id.btn_h_a)
        btn_h_d = findViewById(R.id.btn_h_d)
        btn_f_a = findViewById(R.id.btn_f_a)
        btn_f_d = findViewById(R.id.btn_f_d)
        btn_reset = findViewById(R.id.btn_reset)

        btn_h_a.setOnClickListener() {
            tv = findViewById(R.id.num_h)
            tv.setText((tv.getText().toString().toInt()+1).toString())
            amount()
        }

        btn_h_d.setOnClickListener() {
            tv = findViewById(R.id.num_h)
            num = tv.getText().toString().toInt()
            if (num > 0)
                tv.setText((num-1).toString())
            amount()
        }

        btn_f_a.setOnClickListener() {
            tv = findViewById(R.id.num_f)
            tv.setText((tv.getText().toString().toInt()+1).toString())
            amount()
        }

        btn_f_d.setOnClickListener() {
            tv = findViewById(R.id.num_f)
            num = tv.getText().toString().toInt()
            if (num > 0)
                tv.setText((num-1).toString())
            amount()
        }

        btn_reset.setOnClickListener() {
            tv = findViewById(R.id.num_h)
            tv.setText("0")
            tv = findViewById(R.id.num_f)
            tv.setText("0")
            tv = findViewById(R.id.sum)
            tv.setText("")
            amount()
        }
    }

    fun amount() {
        var sum = 0
        tv = findViewById(R.id.num_h)
        sum += tv.getText().toString().toInt() * 40
        tv = findViewById(R.id.num_f)
        sum += tv.getText().toString().toInt() * 30
        tv = findViewById(R.id.sum)
        if (sum > 0)
            tv.setText("總計 $"+sum.toString())
        else
            tv.setText("")
    }

}