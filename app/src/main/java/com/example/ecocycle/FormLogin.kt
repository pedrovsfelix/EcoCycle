package com.example.ecocycle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_login);
        val btnlogin = findViewById<Button>(R.id.buttonLogin)

        btnlogin?.setOnClickListener() {
            var url = "https://wa.me/5581989943312?text=Ol%C3%A1%20gostaria%20de%20suporte."

            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            })
        }

    }
}