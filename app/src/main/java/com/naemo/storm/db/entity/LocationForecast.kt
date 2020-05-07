package com.naemo.storm.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.naemo.storm.api.models.locationforecast.City
import com.naemo.storm.api.models.locationforecast.ForecastData

@Entity(tableName = "location_forecast")
class LocationForecast (
    @SerializedName("city")
    @Embedded
    val city: City,
    @SerializedName("cnt")
    @PrimaryKey(autoGenerate = false)
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<ForecastData>,
    @SerializedName("message")
    val message: Int
)