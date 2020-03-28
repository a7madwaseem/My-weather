package com.myweather.app.network.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ForecastResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val daysList: List<Day>
) : Parcelable {

}