package bhuwan.example.mapsfinalproject.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bhuwan.example.mapsfinalproject.Database.DatabaseDataClass
import bhuwan.example.mapsfinalproject.Database.DatabaseHelper
import bhuwan.example.mapsfinalproject.R
import bhuwan.example.mapsfinalproject.RecyclerView.Adapter

class PlaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_place, container, false)
        val locationDB = DatabaseHelper(view.context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.RecyclerViewContainer)

        val data = fetchDataFromDB(locationDB)
        Log.e("data:::::::::", "$data")
        val dataList: ArrayList<DatabaseDataClass>

        recyclerView.adapter = context?.let { Adapter(it,data) }
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    private fun fetchDataFromDB(locationDB: DatabaseHelper): ArrayList<DatabaseDataClass>{
        return locationDB.read()
    }

    fun refreshRecyclerView() {
        val locationDB = DatabaseHelper(requireContext())
        val data = fetchDataFromDB(locationDB)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.RecyclerViewContainer)
        recyclerView?.adapter = context?.let { Adapter(it,data) }
    }


}