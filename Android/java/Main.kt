package com.example.traffic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class Main: AppCompatActivity() {

    private lateinit var view: FrameLayout
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    object Pubdata {
        var token = ""
        var lat = 0.0
        var lng = 0.0
        var station = mutableMapOf<String, List<String>>()
        var time = mutableMapOf<String, List<String>>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        view = findViewById(R.id.frame)
        view.visibility = View.GONE

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        enableMyLocation()

        val url = "https://tdx.transportdata.tw/auth/realms/TDXConnect/protocol/openid-connect/token"
        val key = FormBody.Builder()
            .add("grant_type", "client_credentials")
            .add("client_id", BuildConfig.TDX_ID)
            .add("client_secret", BuildConfig.TDX_SECRET)
            .build()
        val req = Request.Builder().url(url).post(key).build()

        OkHttpClient().newCall(req).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                Pubdata.token = Gson().fromJson(response.body?.string(), Token::class.java).access_token
            }

            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Main, "金鑰獲取失敗", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    data class Token (
        var access_token: String
    )

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            }
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    Pubdata.lat = it.latitude
                    Pubdata.lng = it.longitude
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
        }
    }

    fun btn(v: View) {
        when(v.getId()) {
            R.id.metro -> startActivity(Intent(this, Metro::class.java))
            R.id.bus -> startActivity(Intent(this, Bus::class.java))
            R.id.bike -> startActivity(Intent(this, Bike::class.java))
            R.id.road -> startActivity(Intent(this, Road::class.java))
        }
    }

}