package com.myweather.app.network.api

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class Resource<T> private constructor(
    val status: Resource.Status,
    val data: T?,
    val resourceError: ResourceError?
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(resourceError: ResourceError?): Resource<T> {
            return Resource(Status.ERROR, null, resourceError)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}