package com.pujol.weathermap.presentation.searchLocation

import androidx.compose.ui.text.input.TextFieldValue
import com.pujol.weathermap.R
import com.pujol.weathermap.domain.model.LocationCoordinate

data class SearchLocationState(
    val isLoading: Boolean? = false,
    val isError: Boolean = false,
    val locationCoordinates: List<LocationCoordinate> = listOf(),
    val isCallService: Boolean = false,
    val isEmptyLocationCoordinate: Boolean = true,
    val searchHistory: List<LocationCoordinate> = listOf(),
    val isEmptySearchHistory: Boolean = true,
    val showSearchHistory: Boolean = true,
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val iconResource : Int = R.drawable.ic_history
)