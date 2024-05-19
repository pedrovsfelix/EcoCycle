package com.example.ecocycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class FrameMaps : AppCompatActivity() {


    private val places = arrayListOf(
        Place("Estácio", LatLng(-8.0621202,-34.9165972), "Av. Eng. Abdias de Carvalho, 1678 - Madalena, Recife - PE", "Plástico", 4.8f)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            addMarkers(googleMap)

            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()

                places.forEach {
                    bounds.include(it.latLng)
                }

                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
            }
        }
    }

    private fun addMarkers(googleMap: GoogleMap) {
        places.forEach {place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .snippet(place.address)
                    //.snippet(place.category)
                    .position(place.latLng)
                    .icon(
                        BitmapHelper.vectorToBitmap(this, R.drawable.baseline_assistant_photo_24, ContextCompat.getColor(this, R.color.secondary_green))
                    )
            )

            marker?.tag = place
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