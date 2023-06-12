package com.example.tourism

import android.annotation.SuppressLint
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
import com.google.android.gms.location.*
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

class Map : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var view: LinearLayout
    private lateinit var spinner: Spinner
    private lateinit var searchView: SearchView
    private val types = listOf("景點", "餐飲", "旅宿", "活動")
    private val typec = listOf("ScenicSpot", "Restaurant", "Hotel", "Activity")
    private var lat = Main.Pubdata.lat
    private var lng = Main.Pubdata.lng
    var isMarkerClicked = false
    var type = 0
    var search = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        view = findViewById(R.id.first)
        view.visibility = View.GONE

        spinner = findViewById(R.id.spinner)
        spinner.adapter = ArrayAdapter(this, R.layout.spinner, types)
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = position
                getTourism()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        searchView = findViewById(R.id.search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search = query
                getTourism()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    search = ""
                    isMarkerClicked = false
                    getTourism()
                }
                return true
            }
        })

        mapView.getMapAsync { newMap ->
            newMap.setOnCameraIdleListener {
                val newCenter = newMap.cameraPosition.target

                if (!isMarkerClicked && search.isEmpty()) {
                    lat = newCenter.latitude
                    lng = newCenter.longitude
                    getTourism()
                }

                isMarkerClicked = false
            }
        }
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 14f))
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
        googleMap.setOnMarkerClickListener { marker ->
            isMarkerClicked = true
            false
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun getTourism() {
        var url = "https://tdx.transportdata.tw/api/basic/v2/Tourism/${typec[type]}?%24format=JSON&%24"

        search = search.replace(" ", "")
        if (search.isEmpty())
            url += "spatialFilter=nearby(Position,$lat,$lng,5000)"
        else
            url += "filter=contains(${typec[type]}Name,'$search')%20or%20contains(Address,'$search')"

        val req = Request.Builder().url(url).addHeader("authorization", "Bearer "+ Main.Pubdata.token).build()

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val tdx = Gson().fromJson(response.body?.string(), Array<Tourism>::class.java)

                runOnUiThread {
                    googleMap.clear()

                    if (tdx.isNotEmpty())
                        tdx.forEachIndexed { index, data ->
                            var icon = when(type) {
                                0 -> R.drawable.scenicspot
                                1 -> R.drawable.restaurant
                                2 -> R.drawable.hotel
                                else -> R.drawable.activity
                            }

                            var name = when(type) {
                                0 -> data.ScenicSpotName
                                1 -> data.RestaurantName
                                2 -> data.HotelName
                                else -> data.ActivityName
                            }

                            var description = if (data.Description.isNotEmpty())
                                data.Description
                            else
                                data.DescriptionDetail

                            if (description.length > 250)
                                description = description.substring(0, 250)+"..."

                            if (search.isNotEmpty() && index == 0)
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(data.Position!!.PositionLat, data.Position!!.PositionLon)))

                            googleMap.addMarker(
                                MarkerOptions().
                                position(LatLng(data.Position!!.PositionLat, data.Position!!.PositionLon)).
                                icon(BitmapDescriptorFactory.fromResource(icon)).
                                title(name).
                                snippet(description))
                        }
                    else
                        Handler(Looper.getMainLooper()).post {
                            Toast.makeText(this@Map, "查無資料", Toast.LENGTH_SHORT).show()
                        }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Map, "資料獲取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    class Tourism {
        var ScenicSpotName = ""
        var RestaurantName = ""
        var HotelName = ""
        var ActivityName = ""
        var DescriptionDetail = ""
        var Description = ""
        var Position: Position_? = null

        data class Position_(
            var PositionLat: Double,
            var PositionLon: Double
        )
    }

}