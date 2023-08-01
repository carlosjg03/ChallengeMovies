package com.example.movieschallenge.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movieschallenge.R
import com.example.movieschallenge.databinding.FragmentNotificationsBinding
import com.example.movieschallenge.provider.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject


class NotificationsFragment : Fragment() {

    private val binding: FragmentNotificationsBinding by lazy {
        FragmentNotificationsBinding.inflate(layoutInflater, null, false)
    }
    private val viewModelFactory: ViewModelFactory by inject()
    private val viewModel: NotificationsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.google_map) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            viewModel.getLocations()
            viewModel.locations.observe(viewLifecycleOwner) { locations ->
                googleMap.clear()
                locations.forEachIndexed{position, point ->
                    val markerOptions = MarkerOptions()
                    markerOptions.position(LatLng(point.lat,point.lng))
                    markerOptions.title(point.date)
                    googleMap.addMarker(markerOptions)
                    if(position==locations.size-1){
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(point.lat,point.lng), 10f))
                    }
                }

            }


        }


    }

}