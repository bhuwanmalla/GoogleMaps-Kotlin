package bhuwan.example.mapsfinalproject.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//import bhuwan.example.mapsfinalproject.ARG_PARAM1
//import bhuwan.example.mapsfinalproject.ARG_PARAM2
import bhuwan.example.mapsfinalproject.R
import bhuwan.example.mapsfinalproject.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    lateinit var binding: FragmentAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)

    }
}