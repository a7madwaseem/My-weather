package com.myweather.app.ui.fragment.landing

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.myweather.app.R
import com.myweather.app.ui.fragment.BasicFragment
import kotlinx.android.synthetic.main.fragment_landing.*

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class LandingFragment : BasicFragment() {

    //region Fragment Methods
    override fun getContentResource(): Int {
        return R.layout.fragment_landing
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }
    //endregion

    //region General Methods
    private fun setupListeners() {
        weatherButton.setOnClickListener { onStep1Clicked() }
        forecastButton.setOnClickListener { onStep2Clicked() }
    }
    //endregion

    //region UI Handlers
    private fun onStep1Clicked() {
        findNavController().navigate(R.id.action_landingFragment_to_step1Fragment)
    }

    private fun onStep2Clicked() {
        findNavController().navigate(R.id.action_landingFragment_to_step2Fragment)
    }
    //endregion
}