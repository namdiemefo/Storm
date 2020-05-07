package com.naemo.storm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naemo.storm.db.entity.LocationForecast

@Dao
interface LocationForecastDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationForecast(locationForecast: LocationForecast?)

    @Query("SELECT* FROM location_forecast")
    fun loadLocationForecast(): LiveData<LocationForecast>


    @Query("DELETE FROM location_forecast")
    fun deleteLocationForecast()
}