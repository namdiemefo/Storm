package com.naemo.storm.db.entity


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.naemo.storm.api.models.cityweather.*

@Entity(tableName = "city_weather")
data class CityWeather(
    @PrimaryKey(autoGenerate = true)
    val cwId: Int,
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
    @SerializedName("id")
    @ColumnInfo(name = "vId")
    val vId: Int,
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