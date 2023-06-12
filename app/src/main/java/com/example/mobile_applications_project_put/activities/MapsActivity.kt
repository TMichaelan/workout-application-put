package com.example.mobile_applications_project_put.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.mobile_applications_project_put.R
import com.example.mobile_applications_project_put.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mainBinding: ActivityMapsBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private lateinit var googleMap: GoogleMap
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mainBinding.btnLocation.setOnClickListener {
            getLocation()
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Places.initialize(applicationContext, getString(R.string.google_maps_api_key))
        placesClient = Places.createClient(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mainBinding.progressBar.visibility = View.VISIBLE // Show progress spinner
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val lat = location.latitude
                        val lon = location.longitude
                        val currentPos = LatLng(lat, lon)

                        makeApiCall(location)

                        googleMap.addMarker(MarkerOptions().position(currentPos).title("Your location"))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos, 15f))
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    fun makeApiCall(location: Location){
        Thread {
            try {
                val request = Request.Builder()
                    .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location.latitude},${location.longitude}&radius=1500&type=gym&key=AIzaSyAo2f7txNp_qjdZ94Mh6bR353twe1XK2m4")
                    .build()

                val client = OkHttpClient()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {

                        val body = response.body?.string()

                        try {
                            val jsonObject = JSONObject(body)
                            val results = jsonObject.getJSONArray("results")

                            Log.d("test", "OK api call")
                            for (i in 0 until results.length()) {
                                val gym = results.getJSONObject(i)
                                val geometry = gym.getJSONObject("geometry")
                                val location = geometry.getJSONObject("location")
                                val lat = location.getDouble("lat")
                                val lng = location.getDouble("lng")
                                val pos = LatLng(lat, lng)

                                this@MapsActivity.runOnUiThread {
                                    val markerOptions = MarkerOptions()
                                        .position(pos)
                                        .title(gym.getString("name"))
                                        .icon(
                                            BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_BLUE))

                                    googleMap.addMarker(markerOptions)
                                }

                                this@MapsActivity.runOnUiThread {
                                    mainBinding.progressBar.visibility = View.GONE
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            this@MapsActivity.runOnUiThread {
                                mainBinding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
                this@MapsActivity.runOnUiThread {
                    mainBinding.progressBar.visibility = View.GONE
                }
            }
        }.start()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }
}
