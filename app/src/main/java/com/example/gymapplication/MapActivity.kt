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

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

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
        // Static workout spots
        val spot1 = LatLng(45.5017, -73.5673) // Montreal
        val spot2 = LatLng(45.5088, -73.5617) // Another nearby park

        googleMap.addMarker(MarkerOptions().position(spot1).title("Park Workout Spot"))
        googleMap.addMarker(MarkerOptions().position(spot2).title("Outdoor Gym"))

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spot1, 14f))
    }
}
