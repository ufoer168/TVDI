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

class Bus : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var view: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var searchView: SearchView
    private val timer = Timer()
    private val directions = listOf("去程", "返程")
    private var lat = Main.Pubdata.lat
    private var lng = Main.Pubdata.lng
    var isMarkerClicked = false
    var search = ""
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
        drawable = ContextCompat.getDrawable(this, R.drawable.bus)

        mapView.getMapAsync { newMap ->
            newMap.setOnCameraIdleListener {
                val newCenter = newMap.cameraPosition.target

                if (!isMarkerClicked) {
                    lat = newCenter.latitude
                    lng = newCenter.longitude
                    getBus()
                }

                isMarkerClicked = false
            }
        }

        searchView = findViewById(R.id.search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search = query
                getBus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    search = ""
                    isMarkerClicked = false
                    getBus()
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()

        timer.schedule(object : TimerTask() {
            override fun run() {
                getBus()
            }
        }, 1000, 15000)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
        }
        googleMap.isMyLocationEnabled = true
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 15f))
        googleMap.setOnMarkerClickListener { marker ->
            isMarkerClicked = true
            false
        }
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onBackPressed() {
        //停用退回鍵
    }

    private fun getBus() {
        var url = "https://tdx.transportdata.tw/api/advanced/v2/Bus/RealTimeByFrequency/NearBy?%24format=JSON&%24spatialFilter=nearby($lat, $lng, 1000)&%24select=RouteName%2CBusPosition%2CDirection"
        if(search.isNotEmpty())
            url += "&%24filter=contains(RouteName/Zh_tw, '$search')"
        val req = Request.Builder().url(url).addHeader("authorization", "Bearer "+ Main.Pubdata.token).build()

        if (timer.purge() > 0)
            timer.cancel()

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val tdx = Gson().fromJson(response.body?.string(), Array<Car>::class.java)

                runOnUiThread {
                    googleMap.clear()

                    tdx.forEach { data ->
                        googleMap.addMarker(
                            MarkerOptions().
                            position(LatLng(data.BusPosition!!.PositionLat, data.BusPosition!!.PositionLon)).
                            icon(BitmapDescriptorFactory.fromBitmap(addTextToMarker(drawable, data.RouteName!!.Zh_tw))).
                            title(data.RouteName!!.Zh_tw+"("+directions[data.Direction]+")"))
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Bus, "資料獲取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    class Car {
        var RouteName: CarNo? = null
        var BusPosition: CarPosition? = null
        var Direction = 0   //方向

        data class CarNo(
            var Zh_tw: String
        )

        data class CarPosition(
            var PositionLat: Double,
            var PositionLon: Double
        )
    }

    private fun addTextToMarker(drawable: Drawable?, text: String): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        val paint = android.graphics.Paint().apply {
            color = Color.BLACK
            textSize = 22f
            typeface = Typeface.DEFAULT_BOLD
        }

        val textX = (canvas.width - paint.measureText(text)) / 2
        val textY = (canvas.height + paint.textSize) / 2 - 16
        canvas.drawText(text, textX, textY, paint)

        return bitmap
    }

    fun btn(v: View) {
        when(v.getId()) {
            R.id.metro -> startActivity(Intent(this, Metro::class.java))
            R.id.bike -> startActivity(Intent(this, Bike::class.java))
            R.id.road -> startActivity(Intent(this, Road::class.java))
        }
    }

}