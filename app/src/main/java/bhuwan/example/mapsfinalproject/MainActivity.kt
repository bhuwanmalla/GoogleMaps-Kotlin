package bhuwan.example.mapsfinalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import bhuwan.example.mapsfinalproject.Database.DatabaseHelper
import bhuwan.example.mapsfinalproject.Fragments.AboutFragment
import bhuwan.example.mapsfinalproject.Fragments.HomeFragment
import bhuwan.example.mapsfinalproject.Fragments.MapsFragment
import bhuwan.example.mapsfinalproject.Fragments.PlaceFragment
import bhuwan.example.mapsfinalproject.Fragments.ShareFragment
import bhuwan.example.mapsfinalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.home -> {
                transaction.replace(R.id.fragmentContainerView, HomeFragment())
                transaction.commit()
                true
            }

            R.id.maps -> {
                transaction.replace(R.id.fragmentContainerView, MapsFragment(this))
                transaction.commit()
                true
            }

            R.id.place -> {
                transaction.replace(R.id.fragmentContainerView, PlaceFragment())
                transaction.commit()
                true
            }

            R.id.share -> {
                transaction.replace(R.id.fragmentContainerView, ShareFragment())
                transaction.commit()
                true
            }

            R.id.about -> {
                transaction.replace(R.id.fragmentContainerView, AboutFragment())
                transaction.commit()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}