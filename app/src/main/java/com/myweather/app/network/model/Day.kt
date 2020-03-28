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
data class Day(
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("wind")
    val wind: Wind
) : Parcelable {

    fun getFormattedTime() = FormattingUtils.formatTimeOnly(dtTxt)
}