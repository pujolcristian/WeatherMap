package com.pujol.weathermap.data.mapper

import com.pujol.weathermap.data.dto.LocationCoordinateDto
import com.pujol.weathermap.domain.model.LocationCoordinate

fun LocationCoordinateDto.toLocationCoordinate(): LocationCoordinate {
    return LocationCoordinate(
        country = country,
        latitude = latitude,
        longitude = longitude,
        name = name,
        state = state
    )
}