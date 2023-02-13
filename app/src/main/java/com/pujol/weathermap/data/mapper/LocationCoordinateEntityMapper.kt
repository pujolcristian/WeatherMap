package com.pujol.weathermap.data.mapper

import com.pujol.weathermap.data.data_base.entity.LocationCoordinateEntity
import com.pujol.weathermap.domain.model.LocationCoordinate

fun LocationCoordinateEntity.toLocationCoordinate(): LocationCoordinate {
    return LocationCoordinate(
        country = country,
        latitude = latitude,
        longitude = longitude,
        name = name,
        state = state
    )
}
fun LocationCoordinate.toLocationCoordinateEntity(): LocationCoordinateEntity {
    return LocationCoordinateEntity(
        country = country,
        latitude = latitude,
        longitude = longitude,
        name = name,
        state = state
    )
}