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

class Road : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var view: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var searchView: SearchView
    private val timer = Timer()
    private var road = mutableMapOf<String, List<String>>()
    private var lat = Main.Pubdata.lat
    private var lng = Main.Pubdata.lng
    var isMarkerClicked = false
    var search = ""
    var search_ = ""
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

        mapView.getMapAsync { newMap ->
            newMap.setOnCameraIdleListener {
                val newCenter = newMap.cameraPosition.target

                if (!isMarkerClicked) {
                    lat = newCenter.latitude
                    lng = newCenter.longitude
                    getRoad()
                }

                isMarkerClicked = false
            }
        }

        searchView = findViewById(R.id.search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search = query
                getRoad()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    search = ""
                    isMarkerClicked = false
                    getRoad()
                }
                return true
            }
        })

        if (road.isEmpty())
            getRoad()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()

        timer.schedule(object : TimerTask() {
            override fun run() {
                if (road.isNotEmpty())
                    getSpeed()
            }
        }, 1000, 30000)
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

    private fun getRoad() {
        if (search.isNotEmpty() && search != search_)
            search_ = search
        else if (search.isNotEmpty())
            return

        var url = "https://tdx.transportdata.tw/api/basic/v2/Road/Link/"
        url += if (search.isEmpty())
            "GeoLocating/Coordinate/$lng/$lat/500?%24format=JSON&%24select=LinkID%2CRoadName%2CMidPoint"
        else
            "CityRoad/Taipei/$search?%24format=JSON"

        val req = Request.Builder().url(url).addHeader("authorization", "Bearer "+Main.Pubdata.token).build()

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val tdx = Gson().fromJson(response.body?.string(), Array<Roads>::class.java)

                if (tdx.isNotEmpty()) {
                    road = mutableMapOf()
                    tdx.forEachIndexed { index, data ->
                        road[data.LinkID] = listOf(data.RoadName, data.MidPoint.split(",")[1], data.MidPoint.split(",")[0])

                        if (index == 0) {
                            lat = data.MidPoint.split(",")[1].toDouble()
                            lng = data.MidPoint.split(",")[0].toDouble()
                        }
                    }

                    if (search.isNotEmpty() && road.isNotEmpty()) {
                        runOnUiThread {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lng)))
                        }
                    }
                } else if (search.isNotEmpty()) {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(this@Road, "查無[${search}]資料", Toast.LENGTH_SHORT).show()
                    }
                }

                getSpeed()
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Road, "道路資料獲取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    data class Roads (
        var LinkID: String,
        var RoadName: String,
        var MidPoint: String
    )

    private fun getSpeed() {
        if (road.isEmpty()) {
            Toast.makeText(this@Road, "道路資料獲取失敗", Toast.LENGTH_SHORT).show()
            return
        }
        //val url = "https://tdx.transportdata.tw/api/basic/v2/Road/Traffic/Live/VD/City/Taipei?&%24format=JSON"
        val url = "https://tdx.transportdata.tw/api/basic/v2/Road/Traffic/Live/City/Taipei?&%24format=JSON&%24select=SectionID"
        val req = Request.Builder().url(url).addHeader("authorization", "Bearer "+Main.Pubdata.token).build()

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val tdx = Gson().fromJson(response.body?.string(), Speed::class.java)

                runOnUiThread {
                    googleMap.clear()

                    /*tdx.VDLives?.forEach { data ->
                        data.LinkFlows?.forEach { detail ->
                            val linkid = detail.LinkID
                            val speed = detail.Lanes!![0].Speed

                            if (speed > 0.0 && road[linkid]?.isNotEmpty() == true && (search.isEmpty() || search in road[linkid]!![0])) {
                                when(speed) {
                                    in 0.0..15.0 -> drawable = ContextCompat.getDrawable(this@Road, R.drawable.road_r)
                                    in 15.0..35.0 -> drawable = ContextCompat.getDrawable(this@Road, R.drawable.road_y)
                                    else -> drawable = ContextCompat.getDrawable(this@Road, R.drawable.road_g)
                                }

                                googleMap.addMarker(
                                    MarkerOptions().
                                    position(LatLng(road[linkid]!![1].toDouble(), road[linkid]!![2].toDouble())).
                                    icon(BitmapDescriptorFactory.fromBitmap(addTextToMarker(drawable, speed.toString()))).
                                    title(road[linkid]!![0]))
                            }
                        }
                    }*/

                    tdx.LiveTraffics?.forEach { data ->
                        val linkid = data.SectionID.substring(2)
                        val mo = MarkerOptions()

                        if (road[linkid]?.isNotEmpty() == true) {
                            if (data.TravelSpeed > 0 && (search.isEmpty() || search in road[linkid]!![0])) {
                                drawable = when(data.TravelSpeed) {
                                    in 0..20 -> ContextCompat.getDrawable(this@Road, R.drawable.road_r)
                                    in 20..40 -> ContextCompat.getDrawable(this@Road, R.drawable.road_y)
                                    else -> ContextCompat.getDrawable(this@Road, R.drawable.road_g)
                                }

                                mo.icon(BitmapDescriptorFactory.fromBitmap(addTextToMarker(drawable, data.TravelSpeed.toString())))
                            } else if (data.CongestionLevel == -1)
                                mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.close)).snippet("道路封閉")
                            else if (data.TravelSpeed == -99)
                                mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.error)).snippet("資料異常")

                            mo.position(LatLng(road[linkid]!![1].toDouble(), road[linkid]!![2].toDouble()))
                            mo.title(road[linkid]!![0])
                            googleMap.addMarker(mo)
                        }
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Road, "車速資料獲取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    class Speed {
        //var VDLives: Array<VDLives_>? = null
        var LiveTraffics: Array<LiveTraffics_>? = null

        /*class VDLives_ {
            var LinkFlows: Array<LinkFlows_>? = null

            class LinkFlows_ {
                var LinkID = ""
                var Lanes: Array<Lanes_>? = null

                class Lanes_ {
                    var Speed = 0.0
                }
            }
        }*/

        class LiveTraffics_ {
            var SectionID = ""
            var TravelSpeed = 0
            var CongestionLevel = 0
        }
    }

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
        val textY = (canvas.height + paint.textSize) / 2 - 30
        canvas.drawText(text, textX, textY, paint)

        return bitmap
    }

    fun btn(v: View) {
        when(v.getId()) {
            R.id.metro -> startActivity(Intent(this, Metro::class.java))
            R.id.bus -> startActivity(Intent(this, Bus::class.java))
            R.id.bike -> startActivity(Intent(this, Bike::class.java))
        }
    }

}