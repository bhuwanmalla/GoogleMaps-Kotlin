package bhuwan.example.mapsfinalproject.RecyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bhuwan.example.mapsfinalproject.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val listTextView = view.findViewById<TextView>(R.id.listTextView)
}