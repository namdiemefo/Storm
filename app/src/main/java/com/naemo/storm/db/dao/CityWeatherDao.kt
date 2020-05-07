package com.naemo.storm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naemo.storm.db.entity.CityForecast
import com.naemo.storm.db.entity.CityWeather

@Dao
interface CityWeatherDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityWeather(cityWeather: CityWeather?)

    @Query("SELECT* FROM city_weather")
    fun loadCityWeather(): LiveData<CityWeather>

    @Query("SELECT * FROM city_weather WHERE name LIKE '%' || :name || '%'")
    fun searchCityWeather(name: String): CityWeather

    @Query("DELETE FROM city_weather")
    fun deleteCityWeather()
}