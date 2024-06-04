package com.example.ecocycle

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecocycle.adapters.LocalAdapter
import com.example.ecocycle.util.LocalDatabaseHelper

class CooperativaList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooperativa_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = "Listagem de Cooperativas"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, FrameMaps::class.java))
        }

        val db = LocalDatabaseHelper(this)
        val places = db.getAllPlaces()
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val localAdapter = LocalAdapter(places)
        val localDividerDecorator = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)

        recyclerView.adapter = localAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(localDividerDecorator)

    }

}