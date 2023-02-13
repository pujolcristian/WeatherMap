package com.pujol.weathermap.data.repository

import com.pujol.weathermap.core.util.Constants
import com.pujol.weathermap.core.util.Resource
import com.pujol.weathermap.data.dto.LocationTimeDto
import com.pujol.weathermap.data.mapper.toLocationWeather
import com.pujol.weathermap.data.remote.LocationTimeService
import com.pujol.weathermap.data.remote.LocationWeatherService
import com.pujol.weathermap.domain.model.LocationWeather
import com.pujol.weathermap.domain.repository.LocationWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LocationWeatherRepositoryImpl @Inject constructor(
    private val serviceWeather: LocationWeatherService,
    private val serviceTime: LocationTimeService
) :
    LocationWeatherRepository {
    override suspend fun getLocationWeather(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<LocationWeather>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = serviceWeather.getLocationWeather(
                    latitude,
                    longitude,
                    Constants.KEY_SERVICE_WEATHER,
                    Constants.KEY_UNITS
                ).toLocationWeather()
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

    override suspend fun getLocationTime(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = serviceTime.getLocationTime(
                Constants.KEY_SERVICE_TIME,
                Constants.KEY_FORMAT,
                Constants.KEY_BY,
                latitude,
                longitude
            )
            emit(Resource.Loading(false))
            emit(Resource.Success(response.formatted))
        } catch (e: HttpException) {
            emit(Resource.Loading(false))
            emit(Resource.Error(message = e.message()))
        } catch (e: IOException) {
            emit(Resource.Loading(false))
            emit(Resource.Error(message = e.message))
        }
    }
}