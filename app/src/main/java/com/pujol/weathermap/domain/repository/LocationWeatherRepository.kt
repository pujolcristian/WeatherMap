package com.pujol.weathermap.domain.repository

import com.pujol.weathermap.core.util.Resource
import com.pujol.weathermap.data.dto.LocationTimeDto
import com.pujol.weathermap.domain.model.LocationWeather
import kotlinx.coroutines.flow.Flow

interface LocationWeatherRepository {

    suspend fun getLocationWeather(latitude: Double, longitude: Double): Flow<Resource<LocationWeather>>

    suspend fun getLocationTime(latitude: Double, longitude: Double): Flow<Resource<String>>
}
