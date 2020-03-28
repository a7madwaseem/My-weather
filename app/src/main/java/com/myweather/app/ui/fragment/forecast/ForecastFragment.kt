package com.myweather.app.ui.fragment.forecast

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.myweather.app.R
import com.myweather.app.databinding.FragmentForecastBinding
import com.myweather.app.network.model.ForecastResponse
import com.myweather.app.utils.checkPermission
import com.myweather.app.viewmodels.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast.*

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class ForecastFragment : Fragment() {

    //region Member var
    lateinit var viewModel: ForecastViewModel
    private lateinit var dataBinding: FragmentForecastBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var forecastDayAdapter: ForecastAdapter
    //endregion

    //region Fragment Method

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_forecast,
            container,
            false
        )

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup ViewModel
        viewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)

        //setup data binding
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel

        setupViews()
        setupObserver()

        // Init location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
    }

    override fun onResume() {
        super.onResume()
        getLastKnownLocation()
    }
    //endregion

    //region Location Methods
    private fun getLastKnownLocation() {
        if (checkPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                it?.let {
                    // Call API
                    viewModel.callForecastApi(it.latitude.toString(), it.longitude.toString())
                } ?: run {
                    Toast.makeText(
                        context,
                        getString(R.string.fragment_forecast_error_cant_get_location),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            // permission not granted
            cityNameLabel.text = getString(R.string.fragment_forecast_error_cant_get_location)
        }
    }
    //endregion

    //region General Methods
    private fun setupViews() {
        forecastDayAdapter = ForecastAdapter(ArrayList())
        forecastRecycler.layoutManager = LinearLayoutManager(context)
        forecastRecycler.setHasFixedSize(true)
        forecastRecycler.adapter = forecastDayAdapter
        forecastDayAdapter.notifyDataSetChanged()

    }

    private fun setupObserver() {
        viewModel.getForecastResponse().observe(viewLifecycleOwner, Observer {
            adjustViews(it)
        })
    }

    private fun adjustViews(forecastDays: ForecastResponse?) {

        val mappedList = forecastDays?.daysList.let { ForecastMapper().mapFrom(it!!) }
        forecastDayAdapter.itemsList = mappedList
        forecastDayAdapter.notifyDataSetChanged()
    }
    //endregion
}