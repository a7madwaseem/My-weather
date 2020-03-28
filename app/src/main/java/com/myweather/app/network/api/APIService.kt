package com.myweather.app.network.api

import com.myweather.app.network.model.ForecastResponse
import com.myweather.app.network.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
interface APIService {

    @GET(ApiRoutes.GET_CURRENT_WEATHER)
    fun getCurrentWeather(@Query("q") q: String): Call<WeatherResponse>

    @GET(ApiRoutes.GET_FORECAST_WEATHER)
    fun getForeCastWeather(@Query("lat") lat: String, @Query("lon") lon: String): Call<ForecastResponse>

}