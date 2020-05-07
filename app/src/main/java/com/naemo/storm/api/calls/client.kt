package com.naemo.storm.api.calls

import android.content.Context
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.naemo.storm.db.entity.CityForecast
import com.naemo.storm.db.entity.CityWeather
import com.naemo.storm.db.entity.LocationForecast
import com.naemo.storm.db.entity.LocationWeather
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Client {

    private var BASE_URL = "http://api.openweathermap.org/data/2.5/"
    private var service: Service
    var context: Context? = null
        @Inject set

    init {
        this.context = context

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .method(originalRequest.method(), originalRequest.body())
                    .build()

                chain.proceed(newRequest)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(Service::class.java)
    }

    fun getApi(): Service {
        return service
    }
}

interface Service {

    @GET("weather/")
    fun getLocationWeather(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("appid") key: String): Call<LocationWeather>

    @GET("forecast/")
    fun getLocationForecast(@Query("lat") latitude: Double, @Query("lon") longitude: Double, @Query("appid") key: String): Call<LocationForecast>

    @GET("weather/")
    fun getCityWeather(@Query("q") city: String, @Query("appid") key: String): Call<CityWeather>

    @GET("forecast/")
    fun getCityForecast(@Query("q") city: String, @Query("appid") key: String): Call<CityForecast>


}