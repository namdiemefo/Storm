package com.naemo.storm.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.naemo.storm.db.AnyConverter
import com.naemo.storm.db.ArrayConverter
import com.naemo.storm.db.entity.CityForecast
import com.naemo.storm.db.dao.CityForecastDao

@Database(entities = [CityForecast::class], version = 2, exportSchema = false)
@TypeConverters(ArrayConverter::class)
abstract class CityForecastDb: RoomDatabase() {

    abstract fun cityForecastDao(): CityForecastDao

    companion object {
        @Volatile
        private var instance: CityForecastDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(
                    context
                ).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, CityForecastDb::class.java, "City_Forecast")
                .fallbackToDestructiveMigration()
                .build()
    }
}