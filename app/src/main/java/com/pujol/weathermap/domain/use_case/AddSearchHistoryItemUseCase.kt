package com.pujol.weathermap.domain.use_case

import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.domain.repository.LocationCoordinateRepository
import javax.inject.Inject

class AddSearchHistoryItemUseCase @Inject constructor(private val repository: LocationCoordinateRepository) {
    suspend operator fun invoke(locationCoordinate: LocationCoordinate){
        repository.addSearchHistoryItem(locationCoordinate)
    }
}