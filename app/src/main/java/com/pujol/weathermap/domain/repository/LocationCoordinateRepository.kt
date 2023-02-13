package com.pujol.weathermap.domain.repository

import com.pujol.weathermap.core.util.Resource
import com.pujol.weathermap.domain.model.LocationCoordinate
import kotlinx.coroutines.flow.Flow

interface LocationCoordinateRepository {

    fun getSearchHistory(): Flow<List<LocationCoordinate>>

    suspend fun deleteSearchHistoryItem(locationCoordinate: LocationCoordinate)

    suspend fun addSearchHistoryItem(locationCoordinate: LocationCoordinate)

    suspend fun getLocationCoordinate(query: String): Flow<Resource<List<LocationCoordinate>>>
}