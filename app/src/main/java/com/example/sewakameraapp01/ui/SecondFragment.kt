package com.example.sewakameraapp01.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sewakameraapp01.R
import com.example.sewakameraapp01.application.CameraApp
import com.example.sewakameraapp01.databinding.FragmentSecondBinding
import com.example.sewakameraapp01.model.Camera
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val cameraViewModel: CameraViewModel by viewModels {
        CameraViewModelFactory((applicationContext as CameraApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var camera: Camera? = null
    private lateinit var mMap : GoogleMap
    private var currentLatLang: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camera = args.camera
        if (camera != null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text ="ubah"
            binding.nameEditText.setText(camera?.name)
            binding.itemEditText.setText(camera?.item)
            binding.timeEditText.setText(camera?.time)
        }

        // binding google map
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name = binding.nameEditText.text
        val item = binding.itemEditText.text
        val time = binding.timeEditText.text
        binding.saveButton.setOnClickListener {
            if(name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if(item.isEmpty()) {
            Toast.makeText(context, "Item tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if(time.isEmpty()) {
                Toast.makeText(context, "Waktu tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (camera == null) {
                    val camera  = Camera(0, name.toString(), item.toString(), time.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    cameraViewModel.insert(camera)
                } else {
                    val camera  = Camera(camera?.id!!, name.toString(), item.toString(), time.toString(), currentLatLang?.latitude, currentLatLang?.longitude)
                    cameraViewModel.update(camera)
                }
                findNavController().popBackStack() // untuk dismiss halaman ini
            }
        }

        binding.deleteButton.setOnClickListener {
            camera?.let { cameraViewModel.delete(it) }
            findNavController().popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // implement drag marker

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        val sydney = LatLng(-34.0, 151.0)
        mMap.setOnMarkerDragListener(this)

    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {
    }

    private fun checkPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(applicationContext, "Akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        // ngecek jika permission tidak disetujui maka akan berhenti di kondisi if
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // untuk test current location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLatLang = latLang
                    var title = "Marker"
                    //menampilkan lokasi sesuai koordinat yang sudah disimpan atau di update tadi

                    // salah mengecek null, seharusnya camera yang dicek null atau tidak
                    if (camera != null) {
                        title = camera?.name.toString()
                        val newCurrentLocation = LatLng(camera?.latitude!!, camera?.longitude!!)
                        latLang = newCurrentLocation
                    }
                    val markerOption = MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_camera3))
                    mMap.addMarker(markerOption)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang,15f))
                }
            }
    }
}
