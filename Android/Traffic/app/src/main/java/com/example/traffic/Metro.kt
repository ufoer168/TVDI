package com.example.traffic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Timer
import java.util.TimerTask

class Metro : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var view: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var search: SearchView
    private val timer = Timer()
    private val lines = listOf("全線", "板南線", "文湖線", "淡水信義線", "中和新蘆線", "松山新店線", "環狀線")
    private val linec = listOf("", "BL", "BR", "R", "O", "G", "Y")
    var station = Main.Pubdata.station
    var time = Main.Pubdata.time
    var line = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        view = findViewById(R.id.first)
        view.visibility = View.GONE
        search = findViewById(R.id.search)
        search.visibility = View.GONE

        spinner = findViewById(R.id.spinner)
        spinner.adapter = ArrayAdapter(this, R.layout.spinner, lines)
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                line = linec[position]
                if (time.isNotEmpty())
                    getMetro()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        if (station.isEmpty())
            getStation()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()

        timer.schedule(object : TimerTask() {
            override fun run() {
                if (time.isNotEmpty())
                    getMetro()
            }
        }, 1000, 30000)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
        }
        googleMap.isMyLocationEnabled = true
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(Main.Pubdata.lat, Main.Pubdata.lng), 12f))
        googleMap.setInfoWindowAdapter(object: GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(m: Marker): View? {
                return null
            }

            override fun getInfoContents(m: Marker): View {
                val v = layoutInflater.inflate(R.layout.info, null)
                v.findViewById<TextView>(R.id.title).text = m.title
                v.findViewById<TextView>(R.id.snippet).text = m.snippet

                return v
            }
        })
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onBackPressed() {
        //停用退回鍵
    }

    private fun getStation() {
        val url = "https://tdx.transportdata.tw/api/basic/v2/Rail/Metro/Station/TRTC?%24format=JSON&%24select=StationID%2CStationName%2CStationPosition"
        val req = Request.Builder().url(url).addHeader("authorization", "Bearer "+Main.Pubdata.token).build()

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val tdx = Gson().fromJson(response.body?.string(), Array<Station>::class.java)

                tdx.forEach { data ->
                    Main.Pubdata.station[data.StationID] = listOf(data.StationName!!.Zh_tw, data.StationPosition!!.PositionLat.toString(), data.StationPosition!!.PositionLon.toString())
                }

                getTimes()
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Metro, "站點資料獲取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    class Station {
        var StationID = ""
        var StationName: StationName_? = null
        var StationPosition: StationPosition_? = null

        data class StationName_(
            var Zh_tw: String
        )

        data class StationPosition_(
            var PositionLat: Double,
            var PositionLon: Double
        )
    }

    private fun getTimes() {
        val url = "https://tdx.transportdata.tw/api/basic/v2/Rail/Metro/StationTimeTable/TRTC?%24format=JSON&%24filter=ServiceDay%2FMonday%20eq%20true"
        val req = Request.Builder().url(url).addHeader("authorization", "Bearer "+Main.Pubdata.token).build()
        val now = LocalTime.now(ZoneId.of("Asia/Taipei")).format(DateTimeFormatter.ofPattern("HHmm"))
        var at = mutableListOf<String>()
        var sid = ""
        var did = ""
        var i = 0

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val tdx = Gson().fromJson(response.body?.string(), Array<Times>::class.java)

                tdx.forEach { data ->
                    if (sid != data.StationID)
                        i = 0
                    else if (did != data.DestinationStaionID)
                        i++

                    if (at.isNotEmpty() && (sid != data.StationID || did != data.DestinationStaionID)) {
                        at.sort()
                        Main.Pubdata.time[sid+i] = at
                        at = mutableListOf()
                    }

                    if (at.isEmpty())
                        at.add(data.DestinationStationName!!.Zh_tw)

                    data.Timetables!!.forEach { atime ->
                        if (atime.ArrivalTime.replace(":", "") >= now) {
                            at.add(atime.ArrivalTime)
                        }
                    }

                    sid = data.StationID
                    did = data.DestinationStaionID
                }

                if (at.isNotEmpty()) {
                    at.sort()
                    Main.Pubdata.time[sid+i] = at
                }

                getMetro()
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Metro, "到站時間獲取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    class Times {
        var StationID = ""
        var DestinationStaionID = ""
        var DestinationStationName: DestinationStationName_? = null
        var Timetables: Array<Timetables_>? = null

        data class DestinationStationName_(
            var Zh_tw: String
        )

        data class Timetables_(
            var ArrivalTime: String
        )
    }

    private fun getMetro() {
        var url = "https://tdx.transportdata.tw/api/basic/v2/Rail/Metro/LiveBoard/TRTC?%24format=JSON&%24select=StationID%2CTripHeadSign"
        if (line.isNotEmpty())
            url += "&%24filter=startswith(StationID, '$line')"
        val req = Request.Builder().url(url).addHeader("authorization", "Bearer "+Main.Pubdata.token).build()
        val now = LocalTime.now(ZoneId.of("Asia/Taipei")).format(DateTimeFormatter.ofPattern("HHmm"))
        val ths = mutableMapOf<String, String>()
        var sid = ""
        var did = ""
        var i = 0

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val res = response.body?.string()
                if (res?.indexOf("Message") != -1)
                    return

                val tdx = Gson().fromJson(res, Array<Arrival>::class.java)

                tdx.forEach { data ->
                    if (sid != data.StationID)
                        i = 0
                    else if (did != data.TripHeadSign)
                        i++

                    ths[data.StationID + i] = data.TripHeadSign
                    sid = data.StationID
                    did = data.TripHeadSign
                }

                runOnUiThread {
                    googleMap.clear()

                    for ((key, data) in station) {
                        var icon = 0
                        var snippet = ""

                        if (line.isNotEmpty() && key.indexOf(line) != 0)
                            continue

                        for (j in 0..1)
                            if (ths[key + j]?.isNotEmpty() == true) {
                                when (key.substring(0, 2)) {
                                    "BL" -> icon = R.drawable.metro_bl
                                    "BR" -> icon = R.drawable.metro_br
                                }

                                when (key.substring(0, 1)) {
                                    "G" -> icon = R.drawable.metro_g
                                    "O" -> icon = R.drawable.metro_o
                                    "R" -> icon = R.drawable.metro_r
                                    "Y" -> icon = R.drawable.metro_y
                                }

                                snippet += ths[key + j] + "已進站\n"
                            }

                        for (j in 0..5)
                            if (time[key + j]?.isNotEmpty() == true) {
                                val ts = time[key + j]!!.size - 1
                                for (k in 0..ts) {
                                    if (time[key + j]!![ts] in snippet)
                                        break

                                    if (time[key + j]!![k].replace(":", "") >= now) {
                                        snippet += "往" + time[key + j]!![ts] + "將於" + time[key + j]!![k] + "到站\n"
                                        break
                                    }
                                }
                            }

                        if (icon == 0) {
                            when (key.substring(0, 2)) {
                                "BL" -> icon = R.drawable.time_bl
                                "BR" -> icon = R.drawable.time_br
                            }

                            when (key.substring(0, 1)) {
                                "G" -> icon = R.drawable.time_g
                                "O" -> icon = R.drawable.time_o
                                "R" -> icon = R.drawable.time_r
                                "Y" -> icon = R.drawable.time_y
                            }
                        }

                        if (snippet.isNotEmpty())
                            googleMap.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        data[1].toDouble(),
                                        data[2].toDouble()
                                    )
                                ).icon(BitmapDescriptorFactory.fromResource(icon))
                                    .title(data[0])
                                    .snippet(snippet.substring(0, snippet.length - 1))
                            )
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Metro, "到站資料獲取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    data class Arrival(
        var StationID: String,
        var TripHeadSign: String
    )

    fun btn(v: View) {
        when(v.getId()) {
            R.id.bus -> startActivity(Intent(this, Bus::class.java))
            R.id.bike -> startActivity(Intent(this, Bike::class.java))
            R.id.road -> startActivity(Intent(this, Road::class.java))
        }
    }

}