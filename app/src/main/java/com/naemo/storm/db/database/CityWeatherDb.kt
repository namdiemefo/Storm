package com.naemo.storm.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naemo.storm.db.AnyConverter
import com.naemo.storm.db.ArrayConverter
import com.naemo.storm.db.entity.CityWeather
import com.naemo.storm.db.dao.CityWeatherDao

@Database(entities = [CityWeather::class], version = 2, exportSchema = false)
@TypeConverters(ArrayConverter::class)
abstract class CityWeatherDb: RoomDatabase() {

    abstract fun cityWeatherDao(): CityWeatherDao

    companion object {
        @Volatile
        private var instance: CityWeatherDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                ).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, CityWeatherDb::class.java, "City_Weather")
                .fallbackToDestructiveMigration()
                .build()
    }
}