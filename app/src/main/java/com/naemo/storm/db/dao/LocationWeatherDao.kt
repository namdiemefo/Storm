package com.naemo.storm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naemo.storm.db.entity.LocationWeather

@Dao
interface LocationWeatherDao {


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertLocationWeather(locationWeather: LocationWeather?)

        @Query("SELECT* FROM location_weather")
        fun loadLocationWeather(): LiveData<LocationWeather>


        @Query("DELETE FROM location_weather")
        fun deleteLocationWeather()

}