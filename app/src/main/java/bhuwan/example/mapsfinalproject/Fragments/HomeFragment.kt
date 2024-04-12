package bhuwan.example.mapsfinalproject.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import bhuwan.example.mapsfinalproject.R

class HomeFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val backBtn: Button = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            activity?.finish()
        }
        return view
    }
}