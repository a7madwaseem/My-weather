package com.myweather.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.myweather.app.network.api.Resource
import com.myweather.app.network.api.ResourceError
import com.myweather.app.network.model.ForecastResponse
import com.myweather.app.repository.Repository

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class ForecastViewModel : BasicViewModel<ForecastResponse>() {

    private var forecastRepository: Repository = Repository()
    private val forecastResponse: MutableLiveData<ForecastResponse> = MutableLiveData()
    private val callObserver: Observer<Resource<ForecastResponse>> =
        Observer { t -> processResponse(t) }

    fun callForecastApi(lat: String, lon: String) {
        forecastRepository.getForecastWeather(lat, lon).observeForever { callObserver.onChanged(it) }
    }

    fun getForecastResponse(): MutableLiveData<ForecastResponse> {
        return forecastResponse
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
        return error
    }

    override fun getCurrentLocationName(): LiveData<String> {
        return Transformations.map(forecastResponse) { it?.city?.name }
    }

    override fun processResponse(response: Resource<ForecastResponse>?) {
        when (response?.status) {
            Resource.Status.LOADING -> {
                setLoading()
            }
            Resource.Status.SUCCESS -> {
                setSuccess()
                forecastResponse.value = response.data
            }
            Resource.Status.ERROR -> {
                setError()
                error.value = response.resourceError
            }
        }
    }


    public override fun onCleared() {
        super.onCleared()
    }

}