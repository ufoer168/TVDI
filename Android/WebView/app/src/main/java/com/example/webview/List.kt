package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class List : AppCompatActivity() {

    val context = this
    var lv: ListView? = null
    var la: MyListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list)

        la = MyListAdapter()
        lv = findViewById(R.id.listview)
        lv?.adapter = la
        lv?.emptyView = findViewById(R.id.empty)

        lv?.setOnItemClickListener { parent, view, position, id ->
            val r2 = lv?.adapter?.getItem(position)
            val s = "position=" + position + " " + r2.toString()
            Log.d("@@@", s)
        }
    }

    fun click_add(v: View) {
        la?.drawableArrayList?.add(R.drawable.p04)
        val r2 = Result2("Mary", 86, 83)
        la?.stArrayList?.add(r2)
        la?.notifyDataSetChanged()
        Toast.makeText(context, "資料新增完成", Toast.LENGTH_SHORT).show()
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

    inner class MyListAdapter: BaseAdapter() {
        val drawableArrayList = arrayListOf(R.drawable.p01, R.drawable.p02, R.drawable.p03)
        val stArrayList = arrayListOf(Result2("TOM", 100, 99), Result2("Amy", 90, 95), Result2("Jack", 75, 80))

        override fun getCount(): Int {
            return stArrayList.size
        }

        override fun getItem(position: Int): Any {
            return stArrayList.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position + 0L
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val il = context.layoutInflater.inflate(R.layout.list_item, null)
            val iv_image = il.findViewById<ImageView>(R.id.item)
            val tv_name = il.findViewById<TextView>(R.id.tv_n)
            val tv_eng = il.findViewById<TextView>(R.id.tv_e)
            val tv_math = il.findViewById<TextView>(R.id.tv_m)
            val drawableId = drawableArrayList.get(position)
            var r2 = stArrayList.get(position)

            iv_image.setImageResource(drawableId)
            tv_name.text = r2.name
            tv_eng.text = r2.eng.toString()
            tv_math.text = r2.math.toString()

            return il
        }
    }


}