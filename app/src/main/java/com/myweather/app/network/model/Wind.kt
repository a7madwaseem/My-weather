package com.myweather.app.network.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Wind(
    @SerializedName("speed")
    val speed: Double
) : Parcelable {
    fun getWindSeed(): String {
        return speed.toString()
    }
}