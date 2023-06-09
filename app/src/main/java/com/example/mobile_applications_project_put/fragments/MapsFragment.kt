package com.example.mobile_applications_project_put.fragments//package com.example.mobile_applications_project_put.fragments
//
//import androidx.fragment.app.Fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.mobile_applications_project_put.R
//
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//
//class MapsFragment : Fragment() {
//
//    private val callback = OnMapReadyCallback { googleMap ->
//        /**
//         * Manipulates the map once available.
//         * This callback is triggered when the map is ready to be used.
//         * This is where we can add markers or lines, add listeners or move the camera.
//         * In this case, we just add a marker near Sydney, Australia.
//         * If Google Play services is not installed on the device, the user will be prompted to
//         * install it inside the SupportMapFragment. This method will only be triggered once the
//         * user has installed Google Play services and returned to the app.
//         */
//        val sydney = LatLng(-34.0, 151.0)
//        val mountainView = LatLng(37.4, -122.1)
//
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))
//
//
//        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
//        val cameraPosition = CameraPosition.Builder()
//            .target(mountainView) // Sets the center of the map to Mountain View
//            .zoom(17f)            // Sets the zoom
//            .bearing(90f)         // Sets the orientation of the camera to east
//            .tilt(30f)            // Sets the tilt of the camera to 30 degrees
//            .build()              // Creates a CameraPosition from the builder
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_maps, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment?.getMapAsync(callback)
//    }
//}
//
//package com.example.mobile_applications_project_put.fragments
//
//import org.osmdroid.config.Configuration
//import org.osmdroid.util.GeoPoint
//import org.osmdroid.views.MapView
//import org.osmdroid.views.overlay.Marker
//import android.Manifest
//import android.content.Context.MODE_PRIVATE
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.app.ActivityCompat
//import androidx.fragment.app.Fragment
//import com.example.mobile_applications_project_put.R
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory
//import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
//import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
//import java.io.File
//
//class MapsFragment : Fragment() {
//
//    private lateinit var mapView: MapView
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        Configuration.getInstance().load(context, context?.getSharedPreferences("osm", MODE_PRIVATE))
//        Configuration.getInstance().osmdroidBasePath = File(context?.getExternalFilesDir(null), "osm")
//        Configuration.getInstance().osmdroidTileCache = File(context?.getExternalFilesDir(null), "osm/tiles")
//
//        val rootView: View = inflater.inflate(R.layout.fragment_map, container, false)
//        mapView = rootView.findViewById(R.id.map)
//        mapView.setTileSource(TileSourceFactory.MAPNIK)
//
//        // Set default zoom and position
//        mapView.controller.setZoom(10.0)
//        mapView.controller.setCenter(GeoPoint(48.8583, 2.2944)) // for example, coordinates of Paris
//
//        return rootView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Checking for location permissions
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//
//        // Getting user location
//        val locationProvider = GpsMyLocationProvider(context)
//        val myLocationOverlay = MyLocationNewOverlay(locationProvider, mapView)
//        myLocationOverlay.enableMyLocation()
//        mapView.overlays.add(myLocationOverlay)
//
//        // Display markers for gyms
//        displayGymsNearUser()
//    }
//
//    private fun displayGymsNearUser() {
//        // TODO: Implement fetching of nearby gym locations. Replace with real data
//        val gyms = listOf<GeoPoint>(
//            GeoPoint(48.859, 2.294),  // Mockup data
//            GeoPoint(48.858, 2.293),
//            GeoPoint(48.857, 2.294)
//        )
//
//        gyms.forEach { gym ->
//            val marker = Marker(mapView)
//            marker.position = gym
//            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//            mapView.overlays.add(marker)
//        }
//    }
//}
//
//
