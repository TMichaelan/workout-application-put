import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mobile_applications_project_put.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.*
import com.google.android.libraries.places.api.net.*
import com.google.maps.android.SphericalUtil
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private lateinit var getMyLocationButton: Button
    private lateinit var placesClient: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        getMyLocationButton = view.findViewById(R.id.btn_location)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getMyLocationButton.setOnClickListener {
            checkLocationPermission()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        Places.initialize(requireContext(), getString(R.string.google_maps_api_key))
        placesClient = Places.createClient(requireContext())
    }

    private fun addMarkersToMap() {
        val locationRequest = LocationRequest.create() // Create location request.
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY // Set priority.

        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {

                        // Such as:
                        val lat = location.latitude
                        val lon = location.longitude

                        val pos = LatLng(lat, lon)

                        Log.d("test", lat.toString())
                        Log.d("test", lon.toString())

                        googleMap.addMarker(MarkerOptions().position(pos).title("Your location"))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15f))

                    }
                }
            }
        }

        // Create a location provider client and send request for getting location.
        val client = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        client.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun makeApiCall(location: Location){
        val request = Request.Builder().url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location.latitude},${location.longitude}&radius=1500&type=gym&key=AIzaSyAo2f7txNp_qjdZ94Mh6bR353twe1XK2m4")
            .build()

        val response = OkHttpClient().newCall(request).execute().body?.string()
        val jsonObject = JSONObject(response) // This will make the json below as an object for you

        Log.d("Gyms", jsonObject.toString())
        // You can access all the attributes , nested ones using JSONArray and JSONObject here
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            Log.d("Check","1")
            addMarkersToMap()
        }
    }

    companion object {
        private const val DEFAULT_ZOOM = 15f
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123

        private const val SEARCH_RADIUS_KM = 3.0
        private const val ONE_KM_IN_DEGREES = 0.009

        private val SEARCH_RADIUS_DEGREES = SEARCH_RADIUS_KM * ONE_KM_IN_DEGREES
    }
}
