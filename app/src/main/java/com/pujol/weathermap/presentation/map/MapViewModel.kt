package com.pujol.weathermap.presentation.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pujol.weathermap.core.util.Resource
import com.pujol.weathermap.core.util.UiEvent
import com.pujol.weathermap.domain.use_case.GetLocationTimeUseCase
import com.pujol.weathermap.domain.use_case.GetLocationWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getLocationWeatherUseCase: GetLocationWeatherUseCase,
    private val getLocationTimeUseCase: GetLocationTimeUseCase
) : ViewModel() {


    var state by mutableStateOf(MapState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnsetLatLng -> {
                state = state.copy(
                    latLng = event.latLng
                )
                if (event.boolean != null) {
                    state = state.copy(
                        stateLatLng = event.boolean
                    )
                }
                getLocationWeather(event.latLng.latitude, event.latLng.longitude)
                getLocationTime(event.latLng.latitude, event.latLng.longitude)
            }
            MapEvent.OnNavigateToSearch -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(""))
                }
                state = state.copy(
                    stateLatLng = false
                )
            }
        }
    }


    private fun getLocationWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getLocationWeatherUseCase(latitude, longitude).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            state = state.copy(locationWeather = it)
                        }
                    }
                    is Resource.Error -> {
                        state = state.copy(isError = true)

                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = response.loading ?: false)
                    }
                }
            }
        }
    }

    private fun getLocationTime(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getLocationTimeUseCase(latitude, longitude).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        state = state.copy(timeStamp = response.data)
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                }
            }
        }
    }
}