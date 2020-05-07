package com.naemo.storm.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naemo.storm.db.AnyConverter
import com.naemo.storm.db.ArrayConverter
import com.naemo.storm.db.entity.LocationWeather
import com.naemo.storm.db.dao.LocationWeatherDao

@Database(entities = [LocationWeather::class], version = 2, exportSchema = false)
@TypeConverters(ArrayConverter::class)
abstract class LocationWeatherDb: RoomDatabase() {

    abstract fun locationWeather(): LocationWeatherDao

    companion object {
        @Volatile
        private var instance: LocationWeatherDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                ).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, LocationWeatherDb::class.java, "Location_Weather")
                .fallbackToDestructiveMigration()
                .build()
    }
}
