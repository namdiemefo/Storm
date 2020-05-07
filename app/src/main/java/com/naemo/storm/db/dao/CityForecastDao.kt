package com.naemo.storm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naemo.storm.db.entity.CityForecast

@Dao
interface CityForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityForecast(cityForecast: CityForecast?)

    @Query("SELECT* FROM city_forecast")
    fun loadCityForecast(): LiveData<CityForecast>

    @Query("SELECT * FROM city_forecast WHERE name LIKE '%' || :name || '%'")
    fun searchCityForecast(name: String): CityForecast

    @Query("DELETE FROM city_forecast")
    fun deleteCityForecast()
}