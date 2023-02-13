package com.pujol.weathermap.data.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pujol.weathermap.data.data_base.entity.LocationCoordinateEntity

@Database(entities = [LocationCoordinateEntity::class], version = 1)
abstract class WeatherMapDataBase : RoomDatabase() {
    abstract fun locationCoordinateDao(): LocationCoordinateDao
}