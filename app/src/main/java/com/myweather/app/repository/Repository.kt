package com.myweather.app.repository

import androidx.lifecycle.LiveData
import com.myweather.app.network.api.NetworkCall
import com.myweather.app.network.api.Resource
import com.myweather.app.network.http.BaseHttpClient
import com.myweather.app.network.model.ForecastResponse
import com.myweather.app.network.model.WeatherResponse

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class Repository {

    //region Member Val
    private val baseHttpClient = BaseHttpClient()
    private val apiService = baseHttpClient.getApiService()
    //endregion

    //region Callback
    private val currentWeatherCall = NetworkCall<WeatherResponse>()
    private val foreCastWeatherCall = NetworkCall<ForecastResponse>()
    //endregion

    //region API call
    fun getCurrentWeather(query: String): LiveData<Resource<WeatherResponse>> {
        return currentWeatherCall.makeCall(apiService.getCurrentWeather(query))
    }

    fun getForecastWeather(lat: String, lon: String): LiveData<Resource<ForecastResponse>> {
        return foreCastWeatherCall.makeCall(apiService.getForeCastWeather(lat, lon))
    }
    //endregion
}