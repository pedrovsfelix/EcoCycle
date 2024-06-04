package com.example.ecocycle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecocycle.util.UserDatabaseHelper

class FormLogin : AppCompatActivity() {
    private lateinit var dbHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)

        dbHelper = UserDatabaseHelper(this)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            fazerLogin()
        }

        findViewById<Button>(R.id.buttonCadastro).setOnClickListener {
            startActivity(Intent(this, FormCadastro::class.java))
        }
    }

    private fun fazerLogin() {
        val db = dbHelper.readableDatabase

        val email = findViewById<EditText>(R.id.email).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        val cursor = db.rawQuery(
            "SELECT * FROM ${UserDatabaseHelper.TABLE_NAME} WHERE ${UserDatabaseHelper.COLUMN_EMAIL} = ? AND ${UserDatabaseHelper.COLUMN_PASSWORD} = ?",
            arrayOf(email, password)
        )

        if (cursor.moveToFirst()) {
            // Login bem-sucedido
            Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
            // Redirecionar para a tela de cadastro do Local
            val intent = Intent(this, FrameMaps::class.java)
            startActivity(intent)
            finish() // Encerrar esta atividade para que o usuário não possa voltar para a tela de login pressionando o botão "voltar"
        } else {
            // Nome de usuário ou senha incorretos
            Toast.makeText(this, "Nome de usuário ou senha incorretos.", Toast.LENGTH_SHORT).show()
        }

        cursor.close()
    }
}
