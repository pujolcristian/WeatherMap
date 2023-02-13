package com.pujol.weathermap.data.repository

import com.pujol.weathermap.core.util.Constants
import com.pujol.weathermap.core.util.Resource
import com.pujol.weathermap.data.data_base.LocationCoordinateDao
import com.pujol.weathermap.data.mapper.toLocationCoordinate
import com.pujol.weathermap.data.mapper.toLocationCoordinateEntity
import com.pujol.weathermap.data.remote.LocationCoordinateService
import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.domain.repository.LocationCoordinateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LocationCoordinateRepositoryImpl @Inject constructor(
    private val service: LocationCoordinateService,
    private val locationCoordinateDao: LocationCoordinateDao
) :
    LocationCoordinateRepository {
    override fun getSearchHistory(): Flow<List<LocationCoordinate>> {
        return locationCoordinateDao.getLocationCoordinates().map { list ->
            list.map { it.toLocationCoordinate() }
        }
    }

    override suspend fun deleteSearchHistoryItem(locationCoordinate: LocationCoordinate) {
        locationCoordinateDao.deleteLocationCoordinate(
            latitude = locationCoordinate.latitude ?: 0.0,
            longitude = locationCoordinate.longitude ?: 0.0,
            name = locationCoordinate.name ?: ""
        )
    }

    override suspend fun addSearchHistoryItem(locationCoordinate: LocationCoordinate) {
        locationCoordinateDao.addLocationCoordinate(locationCoordinate.toLocationCoordinateEntity())
    }


    override suspend fun getLocationCoordinate(query: String): Flow<Resource<List<LocationCoordinate>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response =
                    service.getLocationCoordinate(
                        query,
                        Constants.KEY_LIMIT,
                        Constants.KEY_SERVICE_WEATHER
                    )
                        .map { it.toLocationCoordinate() }
                emit(Resource.Loading(false))
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                emit(Resource.Loading(false))
                emit(Resource.Error(message = e.message()))
            } catch (e: IOException) {
                emit(Resource.Loading(false))
                emit(Resource.Error(message = e.message))
            }
        }
}