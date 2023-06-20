package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Animation : AppCompatActivity() {

    lateinit var img_arraylist: ArrayList<Int>
    lateinit var btn_start: Button
    lateinit var btn_stop: Button
    lateinit var btn_clear: Button
    lateinit var tv_msg: TextView
    lateinit var img: ImageView
    lateinit var handler: Handler
    lateinit var task: MyTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animation)

        img_arraylist = arrayListOf(R.drawable.p01, R.drawable.p02, R.drawable.p03)
        btn_start = findViewById(R.id.btn_start)
        btn_start.setOnClickListener(StartOnClick())
        btn_stop = findViewById(R.id.btn_stop)
        btn_stop.setOnClickListener(StopOnClick())
        btn_clear = findViewById(R.id.btn_clear)
        btn_clear.setOnClickListener(ClearOnClick())
        tv_msg = findViewById(R.id.tv_msg)
        img = findViewById(R.id.img)
        img.setBackgroundResource(R.drawable.no_image_box)
        handler = Handler(Looper.getMainLooper())
        task = MyTask()
    }

    inner class MyTask: Runnable {
        var i = 0
        var count = 0
        var isDone = false

        override fun run() {
            if (isDone)
                return

            btn_start.isEnabled = false
            tv_msg.text = "i=" + i + " count=" + count

            img.setBackgroundResource(img_arraylist.get(i))

            if (++i == img_arraylist.size)
                i = 0

            handler.postDelayed(this, 1000)

            if (++count > 9) {
                isDone = true
                btn_start.isEnabled = true
            }
        }

        fun reset() {
            i = 0
            count = 0
            isDone = false
        }
    }

    inner class StartOnClick: OnClickListener {
        override fun onClick(v: View?) {
            handler.postDelayed(task, 1000)
        }
    }

    inner class StopOnClick: OnClickListener {
        override fun onClick(v: View?) {
            handler.removeCallbacks(task)
            btn_stop.isEnabled = true

        }
    }

    inner class ClearOnClick: OnClickListener {
        override fun onClick(v: View?) {
            tv_msg.text = "目前沒有訊息"
            img.setBackgroundResource(R.drawable.no_image_box)
            btn_start.isEnabled = true
            task.reset()
        }
    }

}