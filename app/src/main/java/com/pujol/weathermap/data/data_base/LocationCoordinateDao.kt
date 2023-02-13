package com.pujol.weathermap.data.data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pujol.weathermap.data.data_base.entity.LocationCoordinateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationCoordinateDao {

    @Query("SELECT * FROM LocationCoordinateEntity ORDER BY id DESC")
    fun getLocationCoordinates(): Flow<List<LocationCoordinateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocationCoordinate(locationCoordinate: LocationCoordinateEntity)

    @Query("DELETE FROM LocationCoordinateEntity WHERE latitude = :latitude AND longitude = :longitude AND name = :name")
    suspend fun deleteLocationCoordinate(latitude: Double, longitude: Double, name: String)

}