package com.example.ecocycle

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ecocycle.util.LocalDatabaseHelper
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.Locale

class FrameMaps : AppCompatActivity() {

    private lateinit var db: LocalDatabaseHelper
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var autoCompleteFragment: AutocompleteSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_maps)

        //Iniciarlizar API Places (Google)
        Places.initializeWithNewPlacesApiEnabled(this, "", Locale("pt"))
        val placesClient = Places.createClient(this)

        db = LocalDatabaseHelper(this)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            this.googleMap = googleMap
            loadMarkers()
        }

        autoCompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autoCompleteFragment.setHint("Pressione para pesquisar")
        autoCompleteFragment.view?.setBackgroundColor(Color.WHITE)
        autoCompleteFragment.setPlaceFields(listOf(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME, com.google.android.libraries.places.api.model.Place.Field.LAT_LNG))
        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Log.e(null, "$status")
            }

            override fun onPlaceSelected(place: com.google.android.libraries.places.api.model.Place) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(place.latLng))
            }
        })

        // Configura o botão para redirecionar para o FormCadastroLocal
        val buttonToCadastroLocal = findViewById<Button>(R.id.buttonToCadastroLocal)
        buttonToCadastroLocal.setOnClickListener {
            val intent = Intent(this, FormCadastroLocal::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.buttonToListarCooperativa).setOnClickListener {
            val intent = Intent(this, CooperativaList::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        loadMarkers()
    }

    private fun loadMarkers() {
        val places = db.getAllPlaces()
        googleMap.clear() // Limpa todos os marcadores antes de adicionar os novos
        if (places.isNotEmpty()) {
            addMarkers(googleMap, places)
            val bounds = LatLngBounds.builder()

            places.forEach {
                bounds.include(it.latLng)
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
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
