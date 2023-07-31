package com.example.movieschallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.movieschallenge.databinding.FragmentDashboardBinding
import com.example.movieschallenge.databinding.FragmentHomeBinding
import com.example.movieschallenge.provider.ViewModelFactory
import com.example.movieschallenge.ui.home.HomeViewModel
import org.koin.android.ext.android.inject

class DashboardFragment : Fragment() {

    private val binding: FragmentDashboardBinding by lazy {
        FragmentDashboardBinding.inflate(layoutInflater, null, false)
    }
    private val viewModelFactory: ViewModelFactory by inject()
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}