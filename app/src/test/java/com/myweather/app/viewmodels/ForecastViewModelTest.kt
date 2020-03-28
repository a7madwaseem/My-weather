package com.myweather.app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.jraska.livedata.test
import com.myweather.app.network.api.Resource
import com.myweather.app.network.api.ResourceError
import com.myweather.app.network.http.BaseHttpClient
import com.myweather.app.network.model.*
import com.myweather.app.repository.Repository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
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
    fun testWeatherForecastAPIError() {
        // init
        val loadingObserver = viewModel.isLoading().test()
        val errorObserver = viewModel.isError().test()
        val successObserver = viewModel.isSuccess().test()
        val resourceErrorObserver = viewModel.getError().test()
        val error = ResourceError().apply {
            error = Error("No location found")
        }

        val resource: Resource<ForecastResponse> = Resource.error(error)
        forecastResponse.value = resource
        whenever(repository.getForecastWeather(lat, lon)).thenReturn(
            forecastResponse
        )
        viewModel.callForecastApi(lat, lon)

        //Assert
        resourceErrorObserver.assertHasValue().assertValue { it.error == Error("No location found ") }
        loadingObserver.assertHasValue().assertValue(false)
        errorObserver.assertHasValue().assertValue(true)
        successObserver.assertHasValue().assertValue(false)
    }

    @Test
    fun isSuccessTest() {

        // init
        val loadingObserver = viewModel.isLoading().test()
        val errorObserver = viewModel.isError().test()
        val successObserver = viewModel.isSuccess().test()
        val weatherObserver = viewModel.getCurrentLocationName().test()
        val forecastObserver = viewModel.getForecastResponse().test()

        // Response
        val city = City("Dubai")
        val day = Day(1234, "28-3-2020", Main(30.0, 20.0), Wind(10.0))
        val response = ForecastResponse(city, listOf(day))

        val resource: Resource<ForecastResponse> = Resource.success(response)
        forecastResponse.value = resource
        whenever(repository.getForecastWeather(lat, lon)).thenReturn(forecastResponse)
        //viewModel.callForecastApi(lat, lon)

        //assert
        viewModel.getForecastResponse().observeForever( forecastObserver)

       // weatherObserver.assertHasValue().assertValue { it == "Dubai" }
        forecastObserver.assertHasValue().assertValue { it.daysList.size == 1 }
        forecastObserver.assertHasValue().assertValue { it.daysList[0].main.tempMax == 30.0 }
        forecastObserver.assertHasValue().assertValue { it.daysList[0].main.tempMin == 20.0 }
        forecastObserver.assertHasValue().assertValue { it.daysList[0].wind.speed == 20.0 }
        forecastObserver.assertHasValue()
            .assertValue { it.daysList[0].wind.getWindSeed() == "20.0" }
        loadingObserver.assertHasValue().assertValue(false)
        errorObserver.assertHasValue().assertValue(false)
        successObserver.assertHasValue().assertValue(true)
    }

    @Test
    fun testOnCleared() {

       // viewModel.onCleared()
    }
}