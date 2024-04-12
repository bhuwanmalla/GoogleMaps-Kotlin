package bhuwan.example.mapsfinalproject.Fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import bhuwan.example.mapsfinalproject.Database.DatabaseDataClass
import bhuwan.example.mapsfinalproject.Database.DatabaseHelper
import bhuwan.example.mapsfinalproject.MainActivity
import bhuwan.example.mapsfinalproject.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment(context: Context) : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var myMap: GoogleMap
    private var dbHelper = DatabaseHelper(context)
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    private val callback = OnMapReadyCallback { googleMap: GoogleMap ->

        myMap = googleMap
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isCompassEnabled = true
        }
        setupMap()

        myMap.setOnMapClickListener {
            myMap.clear()
            latitude = it.latitude
            longitude = it.longitude
            myMap.addMarker(
                MarkerOptions().position(it)

            )
            myMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var savedBtn: Button = view.findViewById(R.id.saveLocationBtn)
        val activity = requireActivity()
        var dbHelper: DatabaseHelper

//        if (checkPermission()) {
//            loadMap()
//        } else {
//            requestLocationPermission()
//        }

        loadMap()

        savedBtn.setOnClickListener {
            saveData(latitude, longitude)
        }
    }


    private fun saveData(lat: Double, lon: Double) {
        val dialogBuilder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.dialog_box, null)
        val editTextName = dialogView?.findViewById<EditText>(R.id.locationNameEditText)
        val editTextDescription = dialogView?.findViewById<EditText>(R.id.locationDescriptionEditText)

        dialogBuilder.setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Ok") { dialog, _ ->
                val name = editTextName?.text.toString()
                val description = editTextDescription?.text.toString()

                if (name.isBlank()){
                    Toast.makeText(context,"Name is required.", Toast.LENGTH_LONG).show()
                } else {
                    val data = DatabaseDataClass(id = 0, name = name, description = description, latitude = lat, longitude = lon)
                    dbHelper.insert(data, requireContext())
                    dialog.dismiss()
                }
//                // Fetch updated data from the database and update RecyclerView
//                updateRecyclerView()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Add your location name and description")
        alert.show()
    }

    private fun updateRecyclerView() {
        val placeFragment = parentFragment as PlaceFragment?
        placeFragment?.refreshRecyclerView()
    }


    private fun loadMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun setupMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            myMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location: android.location.Location? ->
                if (location != null) {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10f))
                }
            }
        }
    }
}

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                loadMap()
//            } else {
//                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }