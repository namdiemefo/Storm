package com.naemo.storm.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naemo.storm.db.AnyConverter
import com.naemo.storm.db.ArrayConverter
import com.naemo.storm.db.entity.LocationForecast
import com.naemo.storm.db.dao.LocationForecastDao

@Database(entities = [LocationForecast::class], version = 1, exportSchema = false)
@TypeConverters(ArrayConverter::class)
abstract class LocationForecastDb: RoomDatabase() {

    abstract fun locationForecastDao(): LocationForecastDao

    companion object {
        @Volatile
        private var instance: LocationForecastDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                ).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, LocationForecastDb::class.java, "Location_Forecast").build()
    }
}