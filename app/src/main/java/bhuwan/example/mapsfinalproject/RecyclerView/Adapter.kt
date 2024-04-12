package bhuwan.example.mapsfinalproject.RecyclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import bhuwan.example.mapsfinalproject.Database.DatabaseDataClass
import bhuwan.example.mapsfinalproject.R

class Adapter(val context: Context, private val data: ArrayList<DatabaseDataClass>) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_design, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("inside adapter:", "$data")
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationId = data[position].id.toString()
        val locationName = data[position].name
        val locationDescription = data[position].description
        val locationLatitude = data[position].latitude
        val locationLongitude = data[position].longitude

        holder.listTextView.text =
            "${locationId}\n ${locationName}\n ${locationDescription}\n ${locationLatitude} \n ${locationLongitude}"

        holder.listTextView.setOnClickListener {
            Toast.makeText(
                context,
                "LatLng: ${locationLatitude}${locationLongitude}",
                Toast.LENGTH_LONG
            ).show()
        }


    }
}