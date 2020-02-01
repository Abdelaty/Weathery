package com.example.forecastmvvm.ui.weather.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.forecastmvvm.R

class FragmentDetailWeatherFragment : Fragment() {

    companion object {
        fun newInstance() =
            FragmentDetailWeatherFragment()
    }

    private lateinit var viewModel: FragmentDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FragmentDetailWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
