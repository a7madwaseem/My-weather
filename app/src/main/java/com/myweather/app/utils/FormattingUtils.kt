package com.myweather.app.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
class FormattingUtils {
    companion object {

        const val DEFAULT_HOURS_WITHOUT_DATE_FORMAT = "HH:mm a"
        const val DEFAULT_DATE_WITHOUT_TIME_FORMAT = "EEEE, MMMM dd"

        @JvmStatic
        fun formatTemp(value: Double): String? {
            val formatter =
                DecimalFormat("0.##")
            formatter.roundingMode = RoundingMode.DOWN
            return formatter.format(value)
        }

        fun formatTimeOnly(dateString: String): String? {

            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                return SimpleDateFormat(
                    DEFAULT_HOURS_WITHOUT_DATE_FORMAT,
                    Locale.US
                ).format(format.parse(dateString)!!).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "N/A"
        }

        fun formatDateOnly(dateString: String): String {

            val format = SimpleDateFormat("yyyy-MM-dd")
            try {
                return SimpleDateFormat(
                    DEFAULT_DATE_WITHOUT_TIME_FORMAT,
                    Locale.US
                ).format(format.parse(dateString)!!).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return "N/A"
        }
    }
}