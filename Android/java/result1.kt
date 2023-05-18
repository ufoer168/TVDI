package com.example.ufoer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class result1 : AppCompatActivity() {

    private val map: HashMap<Int, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result1)
    }

    fun btn_h(v: View) {
        setResult(RESULT_OK, Intent().putExtra("IMG", R.drawable.hornets))
        finish()
    }

    fun btn_r(v: View) {
        setResult(RESULT_OK, Intent().putExtra("IMG", R.drawable.rockets))
        finish()
    }

}