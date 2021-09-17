package com.example.firebasesampleapp

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.firebasesampleapp.databinding.ActivityMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), FirestoreListener {

    private lateinit var binding: ActivityMainBinding
    private var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isGooglePlayServicesAvailable(this))
            firestore = Firebase.firestore
        else
            showAlert()
    }

    override fun onStart() {
        super.onStart()

        val navController = findNavController(R.id.nav_host_fragment_container)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_write,
            R.id.navigation_list
        ))
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )
        binding.bottomNavigation.setupWithNavController(navController)
    }

    override fun addToFirestore(text: String) {
            firestore?.apply {
                collection("phrases")
                    .add(hashMapOf("phrase" to text))
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@MainActivity, "Success!", Toast.LENGTH_SHORT
                        ).show()
                    }

                    .addOnFailureListener {
                        Toast.makeText(
                            this@MainActivity, "Failure..", Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }

    override fun getFromFirestore(listener: LoadingListener) {
        val phrases = mutableListOf<String>()

        firestore?.apply {
            collection("phrases")
                .get()
                .addOnSuccessListener { response ->
                    for (jsPhrase in response) {
                        phrases.add(jsPhrase.data.getValue("phrase").toString())
                    }
                    listener.updateAdaptersList(phrases)
                    listener.stopLoading()
                }
        }
    }


    private fun isGooglePlayServicesAvailable(context: Context?): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
        return resultCode == ConnectionResult.SUCCESS
    }

    private fun showAlert() {
        val builderAlert = androidx.appcompat.app.AlertDialog.Builder(this)
        builderAlert.setTitle("Google Play Services Error")
        builderAlert.setMessage("Please install Google Play Services!")
        builderAlert.setNegativeButton("Close app") { _: DialogInterface, _: Int ->
            finish()
        }
        builderAlert.show()
    }
}