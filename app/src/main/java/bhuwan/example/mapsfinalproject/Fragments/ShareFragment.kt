package bhuwan.example.mapsfinalproject.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import bhuwan.example.mapsfinalproject.Database.DatabaseDataClass
import bhuwan.example.mapsfinalproject.Database.DatabaseHelper
import bhuwan.example.mapsfinalproject.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ShareFragment : Fragment() {

    private val FILE_NAME = "shared_location.txt"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dbHelper: DatabaseHelper = DatabaseHelper(requireContext())
        val view = inflater.inflate(R.layout.fragment_share, container, false)
        val locationSpinner = view.findViewById<Spinner>(R.id.locationSpinner)
        val shareButton = view.findViewById<Button>(R.id.shareBtn)

        val dbData: ArrayList<DatabaseDataClass> = dbHelper.read()
        val locationsNames = ArrayList<String>()

        for (title in dbData) {
            locationsNames.add(title.name)

        }

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, locationsNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        locationSpinner.setAdapter(adapter)

        shareButton.setOnClickListener {
            shareSelectedLocation(locationSpinner, dbData)
        }

        return view
    }

    private fun shareSelectedLocation(
        locationSpinner: Spinner,
        dbData: ArrayList<DatabaseDataClass>
    ) {
        val selectedLocationName: String = locationSpinner.selectedItem.toString()
        var locationInfo = ""

        for (data in dbData) {
            locationInfo += """
                Name: $selectedLocationName
                Description: ${data.description}
                Coordinates: ${data.latitude}, ${data.longitude}
                
                """.trimIndent()
        }

        val file = writeToFile(locationInfo)

        shareFile(file)
    }

    private fun writeToFile(data: String): File {
        val file = File(context?.filesDir, FILE_NAME)
        return try {
            val fos = FileOutputStream(file)
            fos.write(data.toByteArray())
            fos.close()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            file
        }
    }

    private fun shareFile(file: File) {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "bhuwan.example.mapsfinalproject.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(Intent.createChooser(shareIntent, "Share location via"))
    }
}