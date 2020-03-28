package com.myweather.app.network.api

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
interface ApiRoutes {

    companion object {
        // TODO this should be Encrypted
        const val API_KEY = "6d4f228d73abdb28959b8dd631709ce8"
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        const val GET_CURRENT_WEATHER = "weather?"
        const val GET_FORECAST_WEATHER = "forecast?"

        const val KEY_NAME_APP_ID = "appid"
        const val KEY_NAME_UNIT = "units"
        const val KEY_VALUE_UNIT = "metric"
    }
}