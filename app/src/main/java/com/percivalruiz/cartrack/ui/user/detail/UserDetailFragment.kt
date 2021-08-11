package com.percivalruiz.cartrack.ui.user.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.percivalruiz.cartrack.R
import com.percivalruiz.cartrack.databinding.FragmentUserDetailBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class UserDetailFragment : Fragment(), OnMapReadyCallback {

  private var _binding: FragmentUserDetailBinding? = null
  private val args: UserDetailFragmentArgs by navArgs()
  private val viewModel: UserDetailViewModel by stateViewModel()
  private var map: GoogleMap? = null
  private val binding
    get() = _binding!!

  private val requestPermissionLauncher =
    registerForActivityResult(
      ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
      if (isGranted) {
        setLocationEnabled()
      }
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
      // Get user id from navArgs
      viewModel.getUser(args.id)

      // Set-up UI contents
      viewModel.user.collectLatest {
        it?.run {
          requireActivity().toolbar.title = name
          binding.username.text = username
          binding.email.text = email
          binding.phone.text = phone
          binding.website.text = website
          binding.workName.text = company.name
          binding.workCatchPhrase.text = company.catchPhrase
          binding.workBs.text = company.bs
          binding.address.text =
            "${address.suite}, ${address.street}, ${address.city}, ${address.zipcode}"

          val latLng = this.address.geo.let { geo ->
            LatLng(geo.lat.toDouble(), geo.lng.toDouble())
          }

          map?.run {
            addMarker(
              MarkerOptions()
                .title("$name, $latLng")
                .position(latLng)
            )
            moveCamera(CameraUpdateFactory.newLatLng(latLng))
          }

          binding.markerButton.isEnabled = true
          binding.markerButton.text = "Check out ${name}'s location"
          binding.markerButton.setOnClickListener {
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
          }
        }
      }
    }

    // Initialize map
    val mapFragment: SupportMapFragment =
      childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
  }

  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap

    setLocationEnabled()
  }

  @SuppressLint("MissingPermission")
  private fun setLocationEnabled() {
    if (ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      map?.isMyLocationEnabled = true
      map?.setOnMyLocationButtonClickListener {
        val locationManager =
          requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
          false
        } else {
          Toast.makeText(requireContext(), "Please enable Location service.", Toast.LENGTH_SHORT)
            .show()
          true
        }
      }
    } else {
      requestPermissionLauncher.launch(
        Manifest.permission.ACCESS_FINE_LOCATION
      )
    }
  }
}
