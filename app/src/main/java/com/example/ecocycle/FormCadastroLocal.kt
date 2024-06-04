package com.example.ecocycle

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecocycle.util.LocalDatabaseHelper
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class FormCadastroLocal : AppCompatActivity() {

    private lateinit var editTextNome: EditText
    private lateinit var spinnerMaterial: Spinner
    private lateinit var buttonSalvar: Button
    private lateinit var buttonBack: ImageButton
    private lateinit var autoCompleteFragment: AutocompleteSupportFragment

    private lateinit var address: String
    private lateinit var LatLngAddress: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro_local)

        editTextNome = findViewById(R.id.editTextNome)
        spinnerMaterial = findViewById(R.id.spinnerMaterial)
        buttonSalvar = findViewById(R.id.buttonCadastroLocal)
        buttonBack = findViewById(R.id.buttonBack)

        autoCompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autoCompleteFragment.setHint("Pressione para pesquisar")

        //Especificar abaixo quais dados retonar da API PLACES
        autoCompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS))
        autoCompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Log.e(null, "$status")
            }

            override fun onPlaceSelected(place: Place) {
                address = place.address
                LatLngAddress = place.latLng
            }
        })

        // Configurar Spinner
        val materiais = arrayOf("Vidro", "Plástico", "Metais", "Papelão", "Diversos")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, materiais)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMaterial.adapter = adapter

        // Configura o clique no botão de voltar
        buttonBack.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish() // Finaliza esta atividade para evitar a pilha de atividades acumulada
        }

        buttonSalvar.setOnClickListener {
            salvarLocal()
        }
    }

    private fun salvarLocal() {
        val helper = LocalDatabaseHelper(this)
        val name = findViewById<EditText>(R.id.editTextNome).text.toString()
        val material = spinnerMaterial.selectedItem.toString()

        if (name.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val lat = LatLngAddress.latitude.toString()
        val lng = LatLngAddress.longitude.toString()
        helper.insertLocal(name, lat, lng, address, material)
        Toast.makeText(this, "Local salvo com sucesso!", Toast.LENGTH_SHORT).show()

        // Limpar os campos após salvar
        editTextNome.text.clear()
        autoCompleteFragment.setText("")
        spinnerMaterial.setSelection(0)
    }
}
