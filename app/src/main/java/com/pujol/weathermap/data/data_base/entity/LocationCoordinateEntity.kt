package com.pujol.weathermap.data.data_base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationCoordinateEntity(
    val country: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val name: String? = "",
    val state: String? = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}