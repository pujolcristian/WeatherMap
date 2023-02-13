package com.pujol.weathermap.domain.use_case

import com.pujol.weathermap.core.util.Resource
import com.pujol.weathermap.data.dto.LocationTimeDto
import com.pujol.weathermap.domain.repository.LocationWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocationTimeUseCase @Inject constructor(private val repository: LocationWeatherRepository) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<String>> =
        flow {
            repository.getLocationTime(latitude, longitude).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        emit(Resource.Success(resource.data))
                    }
                    is Resource.Error -> {
                        emit(Resource.Error())
                    }
                    is Resource.Loading -> {
                        emit(Resource.Loading(resource.loading))
                    }
                }
            }
        }
}