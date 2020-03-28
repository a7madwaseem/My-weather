package com.myweather.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myweather.app.network.api.Resource
import com.myweather.app.network.api.ResourceError

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
abstract class BasicViewModel<T> : ViewModel() {


    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isError: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isEmptyResult: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<ResourceError> = MutableLiveData()

    abstract fun isLoading(): LiveData<Boolean>
    abstract fun isSuccess(): LiveData<Boolean>
    abstract fun isError(): LiveData<Boolean>

    abstract fun getError(): LiveData<ResourceError>
    abstract fun getCurrentLocationName(): LiveData<String>
    abstract fun processResponse(response: Resource<T>?)

    fun setSuccess() {
        isLoading.value = false
        isSuccess.value = true
        isError.value = false
        isEmptyResult.value = false
    }

    fun setError() {
        isLoading.value = false
        isSuccess.value = false
        isError.value = true
        isEmptyResult.value = true
    }

    fun setLoading() {
        isLoading.value = true
        isSuccess.value = false
        isError.value = false
        isEmptyResult.value = false
    }
}