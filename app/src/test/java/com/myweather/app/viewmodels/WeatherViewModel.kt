package com.myweather.app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.myweather.app.network.api.Resource
import com.myweather.app.network.model.WeatherResponse
import com.myweather.app.repository.Repository
import com.myweather.app.utils.getOrAwaitValue
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
class WeatherViewModelTest {

    @Suppress("unused")
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherViewModel
    private val repository: Repository = mock()
    private val weatherResponse: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    private val cityName = "Dubai"
    @Before
    @Throws(Exception::class)
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = WeatherViewModel()
    }


    @Test
    fun isLoadingTest() {
        // init
        val loadingObserver = viewModel.isLoading().test()
        val errorObserver = viewModel.isError().test()
        val successObserver = viewModel.isSuccess().test()

        val resource: Resource<WeatherResponse> = Resource.loading(null)
        weatherResponse.value = resource
        whenever(repository.getCurrentWeather(cityName)).thenReturn(weatherResponse)
        viewModel.callCurrentWeatherApi(cityName)

        // assert
        loadingObserver.assertHasValue().assertValue(true)
        errorObserver.assertHasValue().assertValue(false)
        successObserver.assertHasValue().assertValue(false)
    }

    @Test
    fun isErrorTest() {
        // init
        val loadingObserver = viewModel.isLoading().test()
        val errorObserver = viewModel.isError().test()
        val successObserver = viewModel.isSuccess().test()

        val resource: Resource<WeatherResponse> = Resource.loading(null)
        weatherResponse.value = resource
        whenever(repository.getCurrentWeather(cityName)).thenReturn(weatherResponse)
        viewModel.callCurrentWeatherApi(cityName)

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
        val weatherObserver = viewModel.getWeatherResponse()

        viewModel.callCurrentWeatherApi("Dubai")

        assertEquals(weatherObserver.getOrAwaitValue().name, "Dubai")
        assertEquals(cityName.getOrAwaitValue(), "Dubai")

        loadingObserver.assertHasValue().assertValue(false)
        errorObserver.assertHasValue().assertValue(false)
        successObserver.assertHasValue().assertValue(true)
    }
}