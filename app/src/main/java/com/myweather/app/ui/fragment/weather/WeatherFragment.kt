package com.myweather.app.ui.fragment.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myweather.app.R
import com.myweather.app.databinding.FragmentWeatherBinding
import com.myweather.app.network.model.WeatherResponse
import com.myweather.app.utils.InputUtils
import com.myweather.app.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_weather.*

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class WeatherFragment : Fragment() {

    //region Member var
    private lateinit var viewModel: WeatherViewModel
    private lateinit var dataBinding: FragmentWeatherBinding
    private var adapter = WeatherAdapter()
    private val maxCities = 7
    private val minCities = 3
    //endregion

    //region Fragment Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_weather,
            container,
            false
        )

        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup ViewModel
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

        setupViews()
        setupObserver()
        setupListeners()
    }
    //endregion

    //region General Methods
    private fun setupViews() {
        resultRecycler.setHasFixedSize(true)
        resultRecycler.layoutManager = LinearLayoutManager(context)
        resultRecycler.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.getWeatherResponse().observe(viewLifecycleOwner, Observer {
            addItem(it)
        })
    }

    private fun addItem(item: WeatherResponse?) {
        adapter.append(item)
    }

    private fun setupListeners() {
        getDataButton.setOnClickListener { onGetDataClicked() }
    }
    //endregion

    //region UI Handler
    private fun onGetDataClicked() {
        // fresh adapter
        adapter.removeItems()

        // Some Validation
        if (cityNameEdit.text.isNullOrEmpty()) {
            cityNameEdit.error = getString(R.string.fragment_weather_error_this_field_required)
            cityNameEdit.requestFocus()
            return
        } else {
            val cities = cityNameEdit.text.toString().split(",")
            if (cities.size in minCities..maxCities) {
                // Hide keyboard
                InputUtils.hideKeyboard(activity)
                process(cities)
            } else {
                // Error entered cities not in the range
                cityNameEdit.error =
                    getString(R.string.fragment_weather_error_range, minCities, maxCities)
                cityNameEdit.requestFocus()
            }
        }
    }

    private fun process(cities: List<String>) {
        // Call the API
        for (city: String in cities) {
            viewModel.callCurrentWeatherApi(city)
        }
    }
    //endregion
}