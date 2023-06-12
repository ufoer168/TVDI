package com.example.traffic

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class Address: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address)

        mapView = findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        searchView = findViewById(R.id.search)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                geocodeAddress(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty())
                    googleMap.clear()
                return true
            }
        })
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(Main.Pubdata.lat, Main.Pubdata.lng), 15f))
    }

    fun geocodeAddress(query: String) {
        val url = "https://maps.googleapis.com/maps/api/geocode/json?address="+query.replace(" ", "+")+"&key=${BuildConfig.MAP_KEY}"
        val req = Request.Builder().url(url).build()

        GlobalScope.launch(Dispatchers.IO) {
            val res = OkHttpClient().newCall(req).execute()
            val location = JSONObject(res.body?.string()).getJSONArray("results")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONObject("location")
            val lat = location.getDouble("lat")
            val lng = location.getDouble("lng")

            runOnUiThread {
                googleMap.clear()
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lng)))
                googleMap.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(query))
            }
        }
    }

}