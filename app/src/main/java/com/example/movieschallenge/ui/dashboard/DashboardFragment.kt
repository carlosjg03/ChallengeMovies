package com.example.movieschallenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieschallenge.databinding.FragmentDashboardBinding
import com.example.movieschallenge.provider.ViewModelFactory
import org.koin.android.ext.android.inject

class DashboardFragment : Fragment() {

    private val binding: FragmentDashboardBinding by lazy {
        FragmentDashboardBinding.inflate(layoutInflater, null, false)
    }
    private val viewModelFactory: ViewModelFactory by inject()
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }
    private val adapterMostPopular: AdapterMovieAdapter by lazy {
        AdapterMovieAdapter(
            onClick = {

        }, loadMoreItems = {
            viewModel.loadMoreMostPopularMovies()
        })
    }
    private val adapterRated: AdapterMovieAdapter by lazy {
        AdapterMovieAdapter(
            onClick = {

        }, loadMoreItems = {
            viewModel.loadMoreRatedMovies()
        })
    }
    private val adapterRecommendations: AdapterMovieAdapter by lazy {
        AdapterMovieAdapter(
            onClick = {

        }, loadMoreItems = {
            viewModel.loadMoreRecommendationMovies()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovies()
        binding.rvMostPopular.apply {
            adapter = this@DashboardFragment.adapterMostPopular
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
        viewModel.mostPopularMovies.observe(viewLifecycleOwner){adapterMostPopular.putMovies(it)}
        binding.rvRated.apply {
            adapter = this@DashboardFragment.adapterRated
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
        viewModel.topRatedMovies.observe(viewLifecycleOwner){adapterRated.putMovies(it)}
        binding.rvRecommendations.apply {
            adapter = this@DashboardFragment.adapterRecommendations
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
        viewModel.recommendationsMovies.observe(viewLifecycleOwner){adapterRecommendations.putMovies(it)}
    }

}