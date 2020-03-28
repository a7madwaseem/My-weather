package com.myweather.app.utils

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
interface Mapper<R, D> {
    fun mapFrom(type: R): D
}