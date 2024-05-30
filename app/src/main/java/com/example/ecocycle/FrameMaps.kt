package com.example.ecocycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class FrameMaps : AppCompatActivity() {

    private lateinit var db: LocalDatabaseHelper
    private lateinit var places: ArrayList<Place>
    private lateinit var mapFragment: SupportMapFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_maps)

        db = LocalDatabaseHelper(this)
        places = db.getAllPlaces()

        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            if (places.isNotEmpty()) {
                addMarkers(googleMap, places)
                googleMap.setOnMapLoadedCallback {
                    val bounds = LatLngBounds.builder()

                    places.forEach {
                        bounds.include(it.latLng)
                    }

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
                }
            }
        }

        // Configura o botão para redirecionar para o FormCadastroLocal
        val buttonToCadastroLocal = findViewById<Button>(R.id.buttonToCadastroLocal)
        buttonToCadastroLocal.setOnClickListener {
            val intent = Intent(this, FormCadastroLocal::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        mapFragment.getMapAsync { googleMap ->
            addMarkers(googleMap, places)
        }
    }
    private fun addMarkers(googleMap: GoogleMap, places: ArrayList<Place>) {
        places.forEach { place ->
            val color = when (place.category) {
                "Vidro" -> R.color.green
                "Plástico" -> R.color.red
                "Papelão" -> R.color.blue
                "Metais" -> R.color.yellow
                else -> R.color.grey // cor padrão caso a categoria não seja reconhecida
            }

            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .snippet("Endereço: ${place.address}\nAvaliação: ${place.rating}")
                    .position(place.latLng)
                    .icon(
                        BitmapHelper.vectorToBitmap(this, R.drawable.baseline_assistant_photo_24, ContextCompat.getColor(this, color))
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
