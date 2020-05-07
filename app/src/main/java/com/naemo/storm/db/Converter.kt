package com.naemo.storm.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.naemo.storm.api.models.cityforecast.CityForecastData
import com.naemo.storm.api.models.locationforecast.ForecastData
import com.naemo.storm.api.models.locationweather.Weather
import com.naemo.storm.db.entity.CityForecast

class ArrayConverter {

    @TypeConverter
    fun fromAnyList(any: List<Any?>?): String? {
        if (any == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Any?>?>() {}.type
        return gson.toJson(any, type)
    }

    @TypeConverter
    fun toAnyList(string: String?) : MutableList<Any>? {
        if (string ==  null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<Any?>?>() {}.type
        return gson.fromJson(string, type)

    }

    @TypeConverter
    fun fromCityForecastList(any: List<CityForecastData?>?): String? {
        if (any == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<CityForecastData?>?>() {}.type
        return gson.toJson(any, type)
    }

    @TypeConverter
    fun toCityForecastist(string: String?) : MutableList<CityForecastData>? {
        if (string ==  null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<CityForecastData?>?>() {}.type
        return gson.fromJson(string, type)

    }

    @TypeConverter
    fun fromLocationWeatherList(any: List<Weather?>?): String? {
        if (any == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<Weather?>?>() {}.type
        return gson.toJson(any, type)
    }

    @TypeConverter
    fun toLocationWeatherList(string: String?) : MutableList<Weather>? {
        if (string ==  null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<Weather?>?>() {}.type
        return gson.fromJson(string, type)

    }

    @TypeConverter
    fun fromCityWeatherList(any: List<com.naemo.storm.api.models.cityweather.Weather?>?): String? {
        if (any == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<com.naemo.storm.api.models.cityweather.Weather?>?>() {}.type
        return gson.toJson(any, type)
    }

    @TypeConverter
    fun toCityWeatherList(string: String?) : MutableList<com.naemo.storm.api.models.cityweather.Weather>? {
        if (string ==  null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<com.naemo.storm.api.models.cityweather.Weather?>?>() {}.type
        return gson.fromJson(string, type)

    }

    @TypeConverter
    fun fromLocationForecastList(any: List<ForecastData>?): String? {
        if (any == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ForecastData?>?>() {}.type
        return gson.toJson(any, type)
    }

    @TypeConverter
    fun toLocationForecastList(string: String?) : MutableList<ForecastData>? {
        if (string ==  null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<List<ForecastData>?>() {}.type
        return gson.fromJson(string, type)

    }


}

class AnyConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun toString(data: Any?): Int? {
            return data.toString().toIntOrNull()
        }

        @TypeConverter
        @JvmStatic
        fun toData(data: Int?): Any? = data
    }


}