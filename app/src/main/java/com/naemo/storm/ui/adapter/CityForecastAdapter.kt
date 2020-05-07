package com.naemo.storm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naemo.storm.R
import com.naemo.storm.api.models.cityforecast.CityForecastData
import kotlinx.android.synthetic.main.forecast.view.*
import javax.inject.Inject

class CityForecastAdapter(context: Context, private val forecast: List<CityForecastData>): RecyclerView.Adapter<CityForecastAdapter.ForecastViewHolder>(){

    var context: Context? = null
        @Inject set

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val data = forecast[position]
        holder.date.text = data.dtTxt
        holder.pressure.text = data.main.pressure.toString()
        holder.humidity.text = data.main.humidity.toString()
        holder.clouds.text = data.weather[0].description
        holder.temperature.text = data.main.temp.toString()
    }

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.dt
        val pressure: TextView = itemView.pressure
        val humidity: TextView = itemView.humidity
        val clouds: TextView = itemView.clouds
        val temperature: TextView = itemView.temp
    }
}