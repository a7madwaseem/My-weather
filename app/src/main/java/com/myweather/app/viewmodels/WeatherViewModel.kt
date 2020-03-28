package com.myweather.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.myweather.app.network.api.Resource
import com.myweather.app.network.api.ResourceError
import com.myweather.app.network.model.WeatherResponse
import com.myweather.app.repository.Repository

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class WeatherViewModel : BasicViewModel<WeatherResponse>() {

    private var weatherRepository: Repository = Repository()
    private val weatherResponse: MutableLiveData<WeatherResponse> = MutableLiveData()
    private val callObserver: Observer<Resource<WeatherResponse>> =
        Observer { t -> processResponse(t) }

    fun callCurrentWeatherApi(cityName: String) {
        weatherRepository.getCurrentWeather(cityName).observeForever { callObserver.onChanged(it) }
    }

    fun getWeatherResponse(): MutableLiveData<WeatherResponse> {
        return weatherResponse
    }

    override fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    override fun isSuccess(): LiveData<Boolean> {
       return isSuccess
    }

    override fun isError(): LiveData<Boolean> {
     return isError
    }

    override fun getError(): LiveData<ResourceError> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun isEmptyResult(): LiveData<Boolean> {
        return isEmptyResult
    }


    override fun getCurrentLocationName(): LiveData<String> {
        return Transformations.map(weatherResponse) { it?.name }
    }

    override fun processResponse(response: Resource<WeatherResponse>?) {
        when (response?.status) {
            Resource.Status.LOADING -> {
                setLoading()
            }
            Resource.Status.SUCCESS -> {
                setSuccess()
                weatherResponse.value = response.data
            }
            Resource.Status.ERROR -> {
                setError()
                error.value = response.resourceError
            }
        }
    }

}