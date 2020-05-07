package com.naemo.storm.db.entity


import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.naemo.storm.api.models.cityforecast.City
import com.naemo.storm.api.models.cityforecast.CityForecastData

@Entity(tableName = "city_forecast")
data class CityForecast(
    @PrimaryKey(autoGenerate = true)
    val cfId: Int,
    @SerializedName("list")
    val list: List<CityForecastData>,
    @SerializedName("city")
    @Embedded
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Int
)