package com.myweather.app.network.api

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */

class NetworkCall<T> {

    lateinit var call: Call<T>

    fun makeCall(call: Call<T>): MutableLiveData<Resource<T>> {
        this.call = call
        val callBackKt = CallBackKt<T>()
        callBackKt.result.value = Resource.loading(null)
        this.call.clone().enqueue(callBackKt)
        return callBackKt.result
    }

    class CallBackKt<T> : Callback<T> {
        var result: MutableLiveData<Resource<T>> = MutableLiveData()

        override fun onFailure(call: Call<T>, t: Throwable) {
            result.value = Resource.error(ResourceError())
            t.printStackTrace()
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful)
                result.value = Resource.success(response.body())
            else {
                result.value =
                    Resource.error(ResourceError())
            }
        }
    }

    fun cancel() {
        if (::call.isInitialized) {
            call.cancel()
        }
    }
}