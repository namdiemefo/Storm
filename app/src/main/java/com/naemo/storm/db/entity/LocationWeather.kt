package com.naemo.storm.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.naemo.storm.api.models.locationweather.*

@Entity(tableName = "location_weather")
data class LocationWeather(
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    @Embedded
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("coord")
    @Embedded
    val coord: Coord,
    @SerializedName("dt")
    val dt: Int,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    @Embedded
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    @Embedded
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    @Embedded
    val wind: Wind
)