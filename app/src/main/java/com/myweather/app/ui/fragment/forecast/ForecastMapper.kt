package com.myweather.app.ui.fragment.forecast

import com.myweather.app.network.model.Day
import com.myweather.app.network.model.ForecastDayItem
import com.myweather.app.utils.Mapper

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
class ForecastMapper : Mapper<List<Day>, List<ForecastDayItem>> {

    override fun mapFrom(type: List<Day>): List<ForecastDayItem> {

        return type.groupBy {
            it.dtTxt.substringBefore(" ")
        }.flatMap { (key, value) ->
            (listOf(ForecastDayItem.Header(key))) + value.map { ForecastDayItem.Item(it) }
        }
    }
}