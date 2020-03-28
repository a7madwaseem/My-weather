package com.myweather.app.network.http

import com.myweather.app.network.api.APIService
import retrofit2.Retrofit

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
interface HttpClient {
    fun getApiService(): APIService
    fun getRetrofit(): Retrofit
}