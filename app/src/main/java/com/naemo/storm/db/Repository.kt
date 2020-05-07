package com.naemo.storm.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.naemo.storm.api.models.cityforecast.City
import com.naemo.storm.db.database.CityForecastDb
import com.naemo.storm.db.database.CityWeatherDb
import com.naemo.storm.db.database.LocationForecastDb
import com.naemo.storm.db.database.LocationWeatherDb
import com.naemo.storm.db.entity.CityForecast
import com.naemo.storm.db.entity.CityWeather
import com.naemo.storm.db.entity.LocationForecast
import com.naemo.storm.db.entity.LocationWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class Repository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val locationWeatherDao = LocationWeatherDb.invoke(application).locationWeather()
    val locationForecastDao = LocationForecastDb.invoke(application).locationForecastDao()
    val cityWeatherDao = CityWeatherDb.invoke(application).cityWeatherDao()
    val cityForecastDao = CityForecastDb.invoke(application).cityForecastDao()

    fun saveLocationWeather(locationWeather: LocationWeather?) {
        launch {
            save(locationWeather) }
    }

    private suspend fun save(locationWeather: LocationWeather?) {
        withContext(IO) {
           // locationWeatherDao.deleteLocationWeather()
            Log.d("check3", locationWeather.toString())
            locationWeatherDao.insertLocationWeather(locationWeather)
        }
    }

    fun retrieveLocationWeather(): LiveData<LocationWeather> {
        return locationWeatherDao.loadLocationWeather()
    }

    fun saveLocationForecast(locationForecast: LocationForecast?) {
        launch { save(locationForecast) }
    }

    private suspend fun save(locationForecast: LocationForecast?) {
        withContext(IO) {
            locationForecastDao.deleteLocationForecast()
            locationForecastDao.insertLocationForecast(locationForecast)
        }
    }

    fun retrieveLocationForecast(): LiveData<LocationForecast> {
        return locationForecastDao.loadLocationForecast()
    }

    fun saveCityWeather(cityWeather: CityWeather?) {
        launch { save(cityWeather) }
    }

    private suspend fun save(cityWeather: CityWeather?) {
        withContext(IO) {
            cityWeatherDao.deleteCityWeather()
            cityWeatherDao.insertCityWeather(cityWeather)
        }
    }

    fun retrieveCityWeather(): LiveData<CityWeather> {
        return cityWeatherDao.loadCityWeather()
    }

    fun saveCityForecast(cityForecast: CityForecast?) {
        launch { save(cityForecast) }
    }

    private suspend fun save(cityForecast: CityForecast?) {
        withContext(IO) {
            cityForecastDao.deleteCityForecast()
            cityForecastDao.insertCityForecast(cityForecast)
        }
    }

    suspend fun searchCityWeather(name: String): CityWeather {
        return withContext(IO) {
            cityWeatherDao.searchCityWeather(name)
        }
    }

    fun retrieveCityForecast(): LiveData<CityForecast> {
        return cityForecastDao.loadCityForecast()
    }

    suspend fun search(name: String): CityForecast {
        return withContext(IO) {
            cityForecastDao.searchCityForecast(name)
        }
    }
}