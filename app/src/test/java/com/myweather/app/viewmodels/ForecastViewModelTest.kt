package com.myweather.app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.myweather.app.network.api.Resource
import com.myweather.app.network.http.BaseHttpClient
import com.myweather.app.network.model.ForecastResponse
import com.myweather.app.repository.Repository
import com.myweather.app.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */

class ForecastViewModelTest {

    // This rule swaps the background executor used by the Architecture Components
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ForecastViewModel
    private lateinit var httpClient: BaseHttpClient
    private val lifecycleOwner: LifecycleOwner = mock()
    private val repository: Repository = mock()
    private val forecastResponse: MutableLiveData<Resource<ForecastResponse>> =
        MutableLiveData()

    private val lat = "25.2048"
    private val lon = "55.2708"
    private val days = 5

    @Before
    @Throws(Exception::class)
    fun setup() {
        viewModel = ForecastViewModel()
        httpClient = BaseHttpClient()
    }

    @Test
    fun isLoadingTest() {
        // init
        val loadingObserver = viewModel.isLoading().test()
        val errorObserver = viewModel.isError().test()
        val successObserver = viewModel.isSuccess().test()

        val resource: Resource<ForecastResponse> = Resource.loading(null)
        forecastResponse.value = resource
        whenever(repository.getForecastWeather(lat, lon)).thenReturn(forecastResponse)
        viewModel.callForecastApi(lat, lon)

        // assert
        loadingObserver.assertHasValue().assertValue(true)
        errorObserver.assertHasValue().assertValue(false)
        successObserver.assertHasValue().assertValue(false)
    }


    @Test
    fun isSuccessTest() {

        val loadingObserver = viewModel.isLoading().test()
        val errorObserver = viewModel.isError().test()
        val successObserver = viewModel.isSuccess().test()
        val cityName = viewModel.getCurrentLocationName()
        val forecastObserver = viewModel.getForecastResponse()

        viewModel.callForecastApi(lat, lon)
        val resource: Resource<ForecastResponse> = Resource.success(null)
        forecastResponse.value = resource
        whenever(repository.getForecastWeather(lat, lon)).thenReturn(forecastResponse)


        assertEquals(forecastObserver.getOrAwaitValue().daysList.size, 40)
        assertEquals(cityName.getOrAwaitValue(), "Ash Shindaghah")

        loadingObserver.assertHasValue().assertValue(false)
        errorObserver.assertHasValue().assertValue(false)
        successObserver.assertHasValue().assertValue(true)
    }

}