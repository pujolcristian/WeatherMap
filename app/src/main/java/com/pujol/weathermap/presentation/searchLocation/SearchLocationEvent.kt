package com.pujol.weathermap.presentation.searchLocation

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import com.pujol.weathermap.domain.model.LocationCoordinate

sealed class SearchLocationEvent {

    data class OnSetQuery(val textFieldValue: TextFieldValue) : SearchLocationEvent()

    data class OnSearchClick(val context: Context) : SearchLocationEvent()

    data class OnNavigateToMap(val locationCoordinate: LocationCoordinate) : SearchLocationEvent()
    object OnNavigateUp : SearchLocationEvent()
}