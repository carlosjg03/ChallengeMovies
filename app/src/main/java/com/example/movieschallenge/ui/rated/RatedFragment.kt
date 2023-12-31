package com.example.movieschallenge.ui.rated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieschallenge.databinding.FragmentRatedBinding
import com.example.movieschallenge.provider.ViewModelFactory
import org.koin.android.ext.android.inject

class RatedFragment : Fragment() {
    private val binding: FragmentRatedBinding by lazy {
        FragmentRatedBinding.inflate(layoutInflater, null, false)
    }
    private val viewModelFactory: ViewModelFactory by inject()
    private val viewModel: RatedViewModel by viewModels { viewModelFactory }
    private val adapterRated: AdapterRated by lazy {
        AdapterRated{
                viewModel.loadMoreRecommendationMovies()
            }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMoreRecommendationMovies()
        binding.rvRated.apply {
            adapter = this@RatedFragment.adapterRated
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }
        viewModel.ratedMovies.observe(viewLifecycleOwner){adapterRated.putMovies(it)}
    }

}