package com.myweather.app.ui.fragment.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.myweather.app.R
import com.myweather.app.databinding.ListItemForecastDayBinding
import com.myweather.app.databinding.ListItemForecastDayHeaderBinding
import com.myweather.app.network.model.ForecastDayItem

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class ForecastAdapter(var itemsList: List<ForecastDayItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //region View Type
    private enum class ViewType {
        Header,
        Item,
    }
    //endregion

    //region Adapter Methods
    override fun getItemViewType(position: Int): Int {
        val item = itemsList[position]

        return when (item) {
            is ForecastDayItem.Header -> ViewType.Header
            is ForecastDayItem.Item -> ViewType.Item
        }.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (ViewType.values()[viewType]) {
            ViewType.Header -> return HeaderViewHolder(
                DataBindingUtil.inflate<ListItemForecastDayHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_forecast_day_header,
                    parent,
                    false
                )
            )
            ViewType.Item -> return ItemViewHolder(
                DataBindingUtil.inflate<ListItemForecastDayBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_forecast_day,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return if (itemsList.isEmpty()) 0 else itemsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = itemsList[position]

        when (holder) {
            is HeaderViewHolder -> holder.binding.header = item as ForecastDayItem.Header
            is ItemViewHolder -> holder.binding.item = item as ForecastDayItem.Item
        }
    }
    //endregion

    //region View Holder
    inner class HeaderViewHolder : RecyclerView.ViewHolder {

        lateinit var binding: ListItemForecastDayHeaderBinding

        constructor(view: View) : super(view)

        constructor(binding: ListItemForecastDayHeaderBinding) : this(binding.root) {
            this.binding = binding
        }
    }

    inner class ItemViewHolder : RecyclerView.ViewHolder {

        lateinit var binding: ListItemForecastDayBinding

        constructor(view: View) : super(view)

        constructor(binding: ListItemForecastDayBinding) : this(binding.root) {
            this.binding = binding
        }
    }
    //endregion
}