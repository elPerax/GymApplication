package com.example.gymapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        loadWorkoutLocations()
    }

    private fun loadWorkoutLocations() {
        try {
            val inputStream = assets.open("workout_locations.json")
            val json = inputStream.bufferedReader().use(BufferedReader::readText)
            val locations = JSONArray(json)

            for (i in 0 until locations.length()) {
                val loc = locations.getJSONObject(i)
                val name = loc.getString("name")
                val lat = loc.getDouble("latitude")
                val lng = loc.getDouble("longitude")

                val position = LatLng(lat, lng)
                map.addMarker(MarkerOptions().position(position).title(name))
            }

            if (locations.length() > 0) {
                val first = locations.getJSONObject(0)
                val start = LatLng(first.getDouble("latitude"), first.getDouble("longitude"))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 12f))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
