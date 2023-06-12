package com.example.traffic

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

class Bike : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var view: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var search: SearchView
    private val timer = Timer()
    var drawable : Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        view = findViewById(R.id.first)
        view.visibility = View.GONE
        spinner = findViewById(R.id.spinner)
        spinner.visibility = View.GONE
        search = findViewById(R.id.search)
        search.visibility = View.GONE
        drawable = ContextCompat.getDrawable(this, R.drawable.bike)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
        }
        googleMap.isMyLocationEnabled = true
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(Main.Pubdata.lat, Main.Pubdata.lng), 16f))
        getBike()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onBackPressed() {
        //停用退回鍵
    }

    private fun getBike() {
        val url = "https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/youbike_immediate.json"
        val req = Request.Builder().url(url).build()

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val bike = Gson().fromJson(response.body?.string(), Array<UBike>::class.java)

                runOnUiThread {
                    bike.forEach { data ->
                        googleMap.addMarker(
                            MarkerOptions().
                            position(LatLng(data.lat, data.lng)).
                            icon(BitmapDescriptorFactory.fromBitmap(addTextToMarker(drawable, data.sbi.toString()))).
                            title(data.sna.substring(11)).
                            snippet("可借"+data.sbi+"、可還"+data.bemp))
                    }
                }

                timer.schedule(object: TimerTask() {
                    override fun run() {
                        getBike()
                    }
                }, 60000)
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Bike, "資料讀取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    data class UBike (
        var sna: String,
        var lat: Double,
        var lng: Double,
        var sbi: Int,
        var bemp: Int
    )

    private fun addTextToMarker(drawable: Drawable?, text: String): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        val paint = android.graphics.Paint().apply {
            color = Color.BLACK
            textSize = 30f
            typeface = Typeface.DEFAULT_BOLD
        }

        val textX = (canvas.width - paint.measureText(text)) / 2
        val textY = (canvas.height + paint.textSize) / 2 + 14
        canvas.drawText(text, textX, textY, paint)

        return bitmap
    }

    fun btn(v: View) {
        when(v.getId()) {
            R.id.metro -> startActivity(Intent(this, Metro::class.java))
            R.id.bus -> startActivity(Intent(this, Bus::class.java))
            R.id.road -> startActivity(Intent(this, Road::class.java))
        }
    }

}