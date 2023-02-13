package com.pujol.weathermap.domain.use_case

import com.pujol.weathermap.core.util.Resource
import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.domain.repository.LocationCoordinateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import javax.inject.Inject

class GetLocationCoordinatesUseCase @Inject constructor(private val repository: LocationCoordinateRepository){

    suspend operator fun invoke(query: String): Flow<Resource<List<LocationCoordinate>>> = flow {
        repository.getLocationCoordinate(query).collect { resource ->
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