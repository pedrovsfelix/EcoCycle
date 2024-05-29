package com.example.ecocycle

import android.app.Activity
import android.content.ContentValues
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
    private lateinit var dbHelper: LocalDatabaseHelper
    private lateinit var editTextNome: EditText
    private lateinit var editTextEndereco: EditText
    private lateinit var spinnerMaterial: Spinner
    private lateinit var buttonSalvar: Button
    private lateinit var buttonBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro_local)
        dbHelper = LocalDatabaseHelper (this)
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
        val db = dbHelper.writableDatabase

        val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
        val endereco = findViewById<EditText>(R.id.editTextEndereco).text.toString()
        val tipo = findViewById<EditText>(R.id.tipo).text.toString()

        val values = ContentValues().apply {
            put("name", nome)
            put("endereco", endereco)
            put("tipo", tipo)

            }

        val newRowId = db.insert(LocalDatabaseHelper.TABLE_NAME, null, values)
        if (newRowId != -1L) {
            // Usuário criado com sucesso
            Toast.makeText(this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show()
            editTextNome.text.clear()
            editTextEndereco.text.clear()

        } else {
            // Ocorreu um erro ao criar o usuário
            Toast.makeText(this, "Erro ao criar o usuário.", Toast.LENGTH_SHORT).show()
        }
    }
}
