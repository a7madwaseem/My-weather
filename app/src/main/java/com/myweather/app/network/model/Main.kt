package com.myweather.app.network.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.myweather.app.utils.FormattingUtils
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Main(
    @SerializedName("temp_max")
    var tempMax: Double,
    @SerializedName("temp_min")
    var tempMin: Double
) : Parcelable {

    fun getMaxTemp(): String {
        return FormattingUtils.formatTemp(tempMax).toString().plus("°")
    }

    fun getMinTemp(): String {
        return FormattingUtils.formatTemp(tempMin).toString().plus("°")
    }
}