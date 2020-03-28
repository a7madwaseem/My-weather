package com.myweather.app.ui.fragment.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.myweather.app.R
import com.myweather.app.databinding.ListItemWeatherBinding
import com.myweather.app.network.model.WeatherResponse

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //region Member Var
    private var itemsList = ArrayList<WeatherResponse>()
    //endregion

    //region Adapter Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CurrentWeatherViewHolder(
           DataBindingUtil.inflate<ListItemWeatherBinding>(
               LayoutInflater.from(parent.context),
               R.layout.list_item_weather,
               parent,
               false
           )
        )
    }

    override fun getItemCount(): Int {
        return if (itemsList.isEmpty()) 0 else itemsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CurrentWeatherViewHolder).binding.city = itemsList[position]
    }
    //endregion

    //region General Methods
    fun append(item: WeatherResponse?) {
        if (item == null) return
        itemsList.add(item)
        notifyDataSetChanged()
    }

    fun removeItems() {
        itemsList.clear()
    }

    //endregion

    //region View Holder
    inner class CurrentWeatherViewHolder : RecyclerView.ViewHolder {

        lateinit var binding: ListItemWeatherBinding

        constructor(view: View) : super(view)
        constructor(binding: ListItemWeatherBinding) : this(binding.root) {
            this.binding = binding
        }
    }
    //endregion
}