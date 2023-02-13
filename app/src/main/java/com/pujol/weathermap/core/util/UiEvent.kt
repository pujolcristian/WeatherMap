package com.pujol.weathermap.core.util

import com.pujol.weathermap.domain.model.LocationCoordinate

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data class NavigatePop(val locationCoordinate: LocationCoordinate? = LocationCoordinate()) :
        UiEvent()
}
