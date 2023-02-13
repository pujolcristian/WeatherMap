package com.pujol.weathermap.domain.use_case

import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.domain.repository.LocationCoordinateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(private val repository: LocationCoordinateRepository) {
    operator fun invoke(): Flow<List<LocationCoordinate>> {
        return repository.getSearchHistory()
    }
}