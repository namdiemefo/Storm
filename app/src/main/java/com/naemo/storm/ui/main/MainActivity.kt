package com.naemo.storm.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.naemo.storm.BR
import com.naemo.storm.R
import com.naemo.storm.databinding.ActivityMainBinding
import com.naemo.storm.db.entity.CityForecast
import com.naemo.storm.db.entity.CityWeather
import com.naemo.storm.db.entity.LocationForecast
import com.naemo.storm.db.entity.LocationWeather
import com.naemo.storm.ui.adapter.CityForecastAdapter
import com.naemo.storm.ui.adapter.LocationForecastAdapter
import com.naemo.storm.ui.base.BaseActivity
import com.naemo.storm.utils.AppUtils
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    var mainViewModel: MainViewModel? = null
        @Inject set

    var mLayoutId = R.layout.activity_main
        @Inject set

    var appUtils: AppUtils? = null
        @Inject set

    private var mBinder: ActivityMainBinding? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val PERMISSION_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBinding()
        initViews()
    }

    private fun initViews() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStart() {
        super.onStart()
        makeCalls()
    }

    private fun makeCalls() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                getLocation()
            } else {
                appUtils?.showSnackBar(this, main_frame, "Turn on location")
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getLocation() {
        mFusedLocationClient.lastLocation.addOnCompleteListener(this) {task ->
            val location = task.result
            if (location == null) {
                requestNewLocation()
            } else {
                useLocation(location)
            }
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun requestNewLocation() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            val location = p0?.lastLocation
            location?.let { useLocation(it) }
        }
    }

    private fun useLocation(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        getViewModel()?.getLocationWeatherData(latitude, longitude)
        getViewModel()?.getLocationForecastData(latitude, longitude)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Log.d("start", "permissions")
                initViews()
            }
        }
    }

    private fun doBinding() {
        mBinder = getViewDataBinding()
        mBinder?.viewModel = mainViewModel
        mBinder?.navigator = this
        mBinder?.viewModel?.setNavigator(this)
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MainViewModel? {
        return mainViewModel
    }

    override fun getLayoutId(): Int {
        return mLayoutId
    }

    override fun retrieveLocationWeather() {
        val locationWeather = getViewModel()?.loadLocationWeather()
        locationWeather?.observe(this, Observer {
            displayLocationWeather(it)
        })
    }

    private fun displayLocationWeather(it: LocationWeather?) {
        getViewModel()?.locationWeather(it)
    }

    override fun retrieveLocationForecast() {
        val locationForecast = getViewModel()?.loadLocationForecast()
        locationForecast?.observe(this, Observer {
            displayLocationForecast(it)
        })
    }

    private fun displayLocationForecast(it: LocationForecast?) {
        val locationForecastData = it?.list
        val adapter = locationForecastData?.let { it1 -> LocationForecastAdapter(this, it1) }
        location_recycler.adapter = adapter
        location_recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun retrieveCityWeather() {
        val cityWeather = getViewModel()?.loadCityWeather()
        cityWeather?.observe(this, Observer {
            displayCityWeather(it)
        })
    }

    private fun displayCityWeather(it: CityWeather) {
        getViewModel()?.cityWeather(it)
    }

    override fun retrieveCityForecast() {
        val cityForecast = getViewModel()?.loadCityForecast()
        cityForecast?.observe(this, Observer {
            displayCityForecast(it)
        })
    }

    override fun retrieveOldCityForecast(cityForecast: CityForecast?) {
        displayCityForecast(cityForecast)
    }

    override fun retrieveOldCityWeather(cityWeather: CityWeather?) {
        cityWeather?.let { displayCityWeather(it) }
    }

    override fun searchName() {
        hideKeyBoard()
       getViewModel()?.getCityWeatherData()
        getViewModel()?.getCityForecastData()
    }

    override fun showSnackBarMsg(msg: String) {
        appUtils?.showSnackBar(this, main_frame, msg)
    }

    private fun displayCityForecast(it: CityForecast?) {
        val cityForecastData = it?.list
        val adapter = cityForecastData?.let { it1 -> CityForecastAdapter(this, it1) }
        location_recycler.adapter = adapter
        location_recycler.layoutManager = LinearLayoutManager(this)
    }
}
