package com.example.ecocycle

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FrameMaps : AppCompatActivity() {

    // -8.0621202,-34.9165972 Av. Eng. Abdias de Carvalho, 1678 - Madalena, Recife - PE, 50720-225

    private val places = arrayListOf(
        Place("Estácio", LatLng(-8.0621202,-34.9165972), "Av. Eng. Abdias de Carvalho, 1678 - Madalena, Recife - PE", "Plástico", 4.8f)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_frame_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }
    }

    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach {place ->
            googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .snippet(place.address)
                    .snippet(place.category)
                    .position(place.latLng)
            )
        }
    }
}

data class Place(
    val name: String,
    val latLng: LatLng,
    val address: String,
    val category: String,
    val rating: Float
)