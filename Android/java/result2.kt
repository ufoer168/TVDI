package com.example.ufoer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class result2 : AppCompatActivity() {

    val filename = "result.ser"
    var r2: result2_? = null
    var e_name: EditText? = null
    var e_eng: EditText? = null
    var e_math: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result2)

        e_name = findViewById(R.id.name)
        e_eng = findViewById(R.id.eng)
        e_math = findViewById(R.id.math)
    }

    fun btn_s(v: View) {
        var n = e_name?.text.toString()
        var e = e_eng?.text.toString()
        var m = e_math?.text.toString()
        r2 = result2_(n, e, m)

        try {
            val oos = ObjectOutputStream(openFileOutput(filename, Context.MODE_PRIVATE))
            oos.writeObject(r2)
            oos.close()
            Toast.makeText(this, "存檔成功", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            Log.d("找不到檔案", e.toString())
        } catch (e: IOException) {
            Log.d("儲存發生錯誤", e.toString())
        }

        val intent = Intent().putExtra("DATA", r2)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun btn_r(v: View) {
        try {
            val ois = ObjectInputStream(openFileInput(filename))
            r2 = ois.readObject() as result2_
            ois.close()
            Toast.makeText(this, "讀取成功", Toast.LENGTH_SHORT).show()

            e_name?.setText(r2?.name)
            e_eng?.setText(r2?.eng)
            e_math?.setText(r2?.math)
        } catch (e: FileNotFoundException) {
            Log.d("找不到檔案", e.toString())
        } catch (e: IOException) {
            Log.d("讀取發生錯誤", e.toString())
        }
    }

    fun btn_c(v: View) {
        e_name?.setText("")
        e_eng?.setText("")
        e_math?.setText("")
    }

}