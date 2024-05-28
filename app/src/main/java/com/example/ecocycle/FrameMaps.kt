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
        Place("Estácio", LatLng(-8.0621202,-34.9165972), "Av. Eng. Abdias de Carvalho, 1678 - Madalena, Recife - PE", "Plástico", 4.8f),
        Place("Parque das Sucatas", LatLng(-8.0615613, -34.9460815), "Av. Eng. Abdias de Carvalho, 798 - Madalena, Recife - PE, 50720-635", "Metais", 4.5f),
        Place("Fama Reciclagem", LatLng(-8.0626374, -34.9337569), "Av. Manoel Gonçalves da Luz, 288 - Bongi, Recife - PE, 50751-200", "Plástico", 4.7f),
        Place("3R SUCATA", LatLng(-8.0539362, -34.9306753), "Av. Eng. Abdias de Carvalho, 1871 - Bongi, Recife - PE, 50751-655", "Metais", 4.3f),
        Place("JR Reciclagem de Metal", LatLng(-8.0539362, -34.9309114), "R. Clotilde de Oliveira, 251 - Cordeiro, Recife - PE, 50630-090", "Metais", 4.0f),
        Place("Reciclagem Forte", LatLng(-8.0571317, -34.9511878), "R. Dr. Flávio Ferreira da Silva Marojo, 740 - Torrões, Recife - PE, 50761-675", "Vidro", 3.8f),
        Place("Papel Norte", LatLng(-8.0637996,-34.9365128), "R. Prof. Frederico Curió, 337 - Afogados, Recife - PE, 50830-370", "Papelão", 5.0f),
        Place("Macunaíma Reciclagem", LatLng(-8.0742951,-34.9336779), "Estr. dos Remédios, 1561 - Ilha do Retiro, Recife - PE, 50750-360", "Metais", 4.3f),
        Place("Rainha da Sucata", LatLng(-8.0679352,-34.9507112), "Av. Eng. Abdias de Carvalho, 1428 - Torrões, Recife - PE, 50720-225", "Metais", 4.0f),
        Place("Compramos Sucata", LatLng(-8.0725801,-34.9573512), "1ª Tv. Eng. Abdias de Carvalho - Madalena, Recife - PE, 50750-001", "Diversos", 3.8f),
        Place("Reciclemais", LatLng(-8.0787256,-34.9533095), "Endereço - R. José Gomes de Moura, 786 - Estância, Recife - PE, 50865-040", "Diversos", 4.1f),
        Place("FAUSTO AMBIENTAL", LatLng(-8.0795116,-34.947752), "R. São Miguel, 1252 - Afogados, Recife - PE, 50850-000", "Vidro", 2.8f),
        Place("Sucata São Miguel", LatLng(-8.0743666,-34.9440711), "Afogados, Recife - PE, 50770-720", "Diversos", 3.8f),
        Place("Gerecicle Gestão ambiental e Reciclagem", LatLng(-8.0735805,-34.9319457), "R. Realeza, 2270 a - São José, Recife - PE, 50090-690", "Papelão", 4.3f),
        Place("Kaper Reciclagem", LatLng(-8.0735805,-34.9319457), "R. Imperial, 2049 - São José, Recife - PE, 50080-150", "Plástico", 4.0f),
        Place("Ecoóleo - Coleta De Óleo Usado", LatLng(-8.0710794,-34.9615374), "R. Dr. Aniceto Ribeiro Varejão, 15-A - Torrões, Recife - PE, 50761-080", "Oléo", 3.8f)
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
