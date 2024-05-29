package com.example.ecocycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormCadastroLocal : AppCompatActivity() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextEndereco: EditText
    private lateinit var spinnerMaterial: Spinner
    private lateinit var buttonSalvar: Button
    private lateinit var buttonBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro_local)

        editTextNome = findViewById(R.id.editTextNome)
        editTextEndereco = findViewById(R.id.editTextEndereco)
        spinnerMaterial = findViewById(R.id.spinnerMaterial)
        buttonSalvar = findViewById(R.id.buttonCadastroLocal)
        buttonBack = findViewById(R.id.buttonBack)

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
        val nome = editTextNome.text.toString()
        val endereco = editTextEndereco.text.toString()
        //val material = spinnerMaterial.selectedItem.toString()

        if (nome.isEmpty() || endereco.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        } else {
            // Salvar os dados no banco de dados ou em outro local
            Toast.makeText(this, "Local salvo com sucesso!", Toast.LENGTH_SHORT).show()

            // Limpar os campos após salvar
            editTextNome.text.clear()
            editTextEndereco.text.clear()
            spinnerMaterial.setSelection(0)
        }
    }
}
