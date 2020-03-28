package com.myweather.app.network.model

import com.myweather.app.utils.FormattingUtils

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
sealed class ForecastDayItem {
    data class Header(val date: String) : ForecastDayItem() {
        fun getFormattedDate(): String = FormattingUtils.formatDateOnly(date)
    }

    data class Item(val day: Day) : ForecastDayItem()
}