package com.example.ecocycle

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecocycle.util.UserDatabaseHelper


class FormCadastro : AppCompatActivity() {
    private lateinit var dbHelper: UserDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_cadastro)

        dbHelper = UserDatabaseHelper(this)

        val buttonCadastrar = findViewById<Button>(R.id.btnCadastro)
        buttonCadastrar.setOnClickListener {
            cadastrarUsuario()
        }

        val buttonReturn = findViewById<ImageView>(R.id.btnReturn)
        buttonReturn.setOnClickListener {
            retornar()
        }
    }

    private fun limparCampos() {
        findViewById<EditText>(R.id.username).text.clear()
        findViewById<EditText>(R.id.usermail).text.clear()
        findViewById<EditText>(R.id.userpwd).text.clear()
    }

    private fun cadastrarUsuario() {
        val db = dbHelper.writableDatabase

        val name = findViewById<EditText>(R.id.username).text.toString()
        val mail = findViewById<EditText>(R.id.usermail).text.toString()
        val pwd = findViewById<EditText>(R.id.userpwd).text.toString()

        val values = ContentValues().apply {
            put("username", name)
            put("email", mail)
            put("password", pwd)

        }

        val newRowId = db.insert(UserDatabaseHelper.TABLE_NAME, null, values)
        if (newRowId != -1L) {
            // Usuário criado com sucesso
            Toast.makeText(this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show()
            limparCampos()
            retornar()
        } else {
            // Ocorreu um erro ao criar o usuário
            Toast.makeText(this, "Erro ao criar o usuário.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun retornar() {
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
    }

}
