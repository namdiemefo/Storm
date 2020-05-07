package com.naemo.storm.ui.main

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import com.naemo.storm.BuildConfig
import com.naemo.storm.R
import com.naemo.storm.api.calls.Client
import com.naemo.storm.db.Repository
import com.naemo.storm.db.entity.CityForecast
import com.naemo.storm.db.entity.CityWeather
import com.naemo.storm.db.entity.LocationForecast
import com.naemo.storm.db.entity.LocationWeather
import com.naemo.storm.ui.base.BaseViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel(application: Application) : BaseViewModel<MainNavigator>(application), CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = Main

    var search = ObservableField("")
    var name = ObservableField("")
    var pressure = ObservableField("")
    var humidity = ObservableField("")
    var clouds = ObservableField("")
    var temperature = ObservableField("")

    val repository = Repository(application)

    var client = Client()
        @Inject set

    fun getCityForecastData() {
        val name = search.get().toString()
        val cityForecastResponseCall: Call<CityForecast> = client.getApi().getCityForecast(name, BuildConfig.WEATHER_API_TOKEN)
        cityForecastResponseCall.enqueue(object : Callback<CityForecast> {
            override fun onResponse(call: Call<CityForecast>, response: Response<CityForecast>) {
                if (response.isSuccessful) {
                    val cityForecast = response.body()
                    saveCityForecast(cityForecast)
                    getNavigator()?.retrieveOldCityForecast(cityForecast)
                } else {
                    launch {
                        val cityForecast = repository.search(name)
                        cityForecast.let { getNavigator()?.retrieveOldCityForecast(it)  }
                    }
                }
            }

            override fun onFailure(call: Call<CityForecast>, t: Throwable) {
                if (t is  IOException) {
                    call.cancel()
                    launch {
                        val cityForecast = repository.search(name)
                        cityForecast.let { getNavigator()?.retrieveOldCityForecast(it)  }
                    }
                }
            }
        })
    }

    fun getCityWeatherData() {
        val name = search.get().toString()
        val cityWeatherResponseCall: Call<CityWeather> = client.getApi().getCityWeather(name, BuildConfig.WEATHER_API_TOKEN)
        cityWeatherResponseCall.enqueue(object : Callback<CityWeather> {
            override fun onResponse(call: Call<CityWeather>, response: Response<CityWeather>) {
                if (response.isSuccessful) {
                    val cityWeather = response.body()
                    saveCityWeather(cityWeather)
                    cityWeather?.let { cityWeather(it) }
                } else {
                    launch {
                        val cityWeather = repository.searchCityWeather(name)
                        if (cityWeather == null) {
                            getNavigator()?.showSnackBarMsg("$name was not found, check internet connectivity")
                        } else  {
                            cityWeather(cityWeather)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                if (t is IOException) {
                    call.cancel()
                    launch {
                        val cityWeather = repository.searchCityWeather(name)
                        if (cityWeather == null) {
                            getNavigator()?.showSnackBarMsg("$name was not found, check internet connectivity")
                        } else  {
                            cityWeather(cityWeather)
                        }
                    }
                }
            }
        })
    }

    fun getLocationForecastData(latitude: Double, longitude: Double) {
        val locationForecastResponseCall: Call<LocationForecast> = client.getApi().getLocationForecast(latitude, longitude, BuildConfig.WEATHER_API_TOKEN)
        locationForecastResponseCall.enqueue(object : Callback<LocationForecast> {
            override fun onResponse(call: Call<LocationForecast>, response: Response<LocationForecast>) {
                if (response.isSuccessful) {
                    val locationForecast = response.body()
                    saveLocationForecast(locationForecast)
                    getNavigator()?.retrieveLocationForecast()
                } else {
                    getNavigator()?.retrieveLocationForecast()
                }
            }

            override fun onFailure(call: Call<LocationForecast>, t: Throwable) {
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.retrieveLocationForecast()
                }
            }
        })
    }


    fun getLocationWeatherData(latitude: Double, longitude: Double) {
        val locationWeatherResponseCall: Call<LocationWeather> = client.getApi().getLocationWeather(latitude, longitude, BuildConfig.WEATHER_API_TOKEN)
        locationWeatherResponseCall.enqueue(object : Callback<LocationWeather> {
            override fun onResponse(call: Call<LocationWeather>, response: Response<LocationWeather>) {
                    val locationWeather = response.body()
                    Log.d("check1", locationWeather.toString())
                    saveLocationWeather(locationWeather)
                    getNavigator()?.retrieveLocationWeather()

            }

            override fun onFailure(call: Call<LocationWeather>, t: Throwable) {
                if (t is IOException) {
                    call.cancel()
                    getNavigator()?.retrieveLocationWeather()
                }
            }
        })
    }

    fun locationWeather(locationWeather: LocationWeather?) {
        name.set(locationWeather?.name)
        pressure.set(locationWeather?.main?.pressure.toString())
        humidity.set(locationWeather?.main?.humidity.toString())
        clouds.set(locationWeather?.weather?.get(0)?.description)
        temperature.set(locationWeather?.main?.temp.toString())
    }

    fun cityWeather(cityWeather: CityWeather) {
        name.set(cityWeather.name)
        pressure.set(cityWeather.main.pressure.toString())
        humidity.set(cityWeather.main.humidity.toString())
        clouds.set(cityWeather.weather[0].description)
        temperature.set(cityWeather.main.temp.toString())
    }

    fun saveLocationWeather(locationWeather: LocationWeather?)  {
        repository.saveLocationWeather(locationWeather)
    }

    fun saveLocationForecast(locationForecast: LocationForecast?) {
        repository.saveLocationForecast(locationForecast)
    }

    fun saveCityWeather(cityWeather: CityWeather?) {
        repository.saveCityWeather(cityWeather)
    }

    fun saveCityForecast(cityForecast: CityForecast?) {
        repository.saveCityForecast(cityForecast)
    }

    fun loadLocationWeather(): LiveData<LocationWeather> {
        return repository.retrieveLocationWeather()
    }

    fun loadLocationForecast(): LiveData<LocationForecast> {
        return repository.retrieveLocationForecast()
    }

    fun loadCityWeather(): LiveData<CityWeather> {
        return repository.retrieveCityWeather()
    }

    fun loadCityForecast(): LiveData<CityForecast> {
        return repository.retrieveCityForecast()
    }

}


interface MainNavigator {

    fun retrieveLocationWeather()

    fun retrieveLocationForecast()

    fun retrieveCityWeather()

    fun retrieveCityForecast()

    fun retrieveOldCityForecast(cityForecast: CityForecast?)

    fun retrieveOldCityWeather(cityWeather: CityWeather?)

    fun searchName()

    fun showSnackBarMsg(msg: String)
}

@Module
class MainModule {

    @Provides
    fun providesMainViewModel(application: Application): MainViewModel {
        return MainViewModel(application)
    }

    @Provides
    fun layoutId(): Int {
        return R.layout.activity_main
    }
}