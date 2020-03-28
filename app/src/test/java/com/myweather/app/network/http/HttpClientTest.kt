package com.myweather.app.network.http

import org.junit.Assert
import org.junit.Test

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
class HttpClientTest {

    @Test
    fun testHttpClient() {

        val expectedBaseUrl = "http://api.openweathermap.org/data/2.5/"
        val httpClient = BaseHttpClient()

        // Null test
        Assert.assertNotNull(httpClient.getApiService())
        Assert.assertNotNull(httpClient.getRetrofit())
        Assert.assertNotNull(httpClient.getRetrofit().baseUrl().host() == expectedBaseUrl)

    }
}