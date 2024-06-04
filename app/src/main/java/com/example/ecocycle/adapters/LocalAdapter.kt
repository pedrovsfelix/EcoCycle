package com.example.ecocycle.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecocycle.Place
import com.example.ecocycle.R

class LocalAdapter(private val places: List<Place>) :
    RecyclerView.Adapter<LocalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: TextView = view.findViewById(R.id.textView_name)
        var textViewAddress: TextView = view.findViewById(R.id.textView_address)
        var imageViewLocal: ImageView = view.findViewById(R.id.imageView_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cooperativa_list_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount() = places.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]

        holder.textViewName.text = place.name
        holder.textViewAddress.text = place.address
        holder.imageViewLocal.setImageResource(R.drawable.bck_ecycle)
    }
}