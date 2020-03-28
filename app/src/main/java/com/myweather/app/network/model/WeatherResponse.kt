package com.myweather.app.network.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class WeatherResponse(
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("wind")
    val wind: Wind
) : Parcelable