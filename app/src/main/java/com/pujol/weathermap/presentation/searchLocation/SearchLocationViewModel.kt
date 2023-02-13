package com.pujol.weathermap.presentation.searchLocation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pujol.weathermap.R
import com.pujol.weathermap.core.util.Resource
import com.pujol.weathermap.core.util.UiEvent
import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.domain.use_case.AddSearchHistoryItemUseCase
import com.pujol.weathermap.domain.use_case.DeleteSearchHistoryItemUseCase
import com.pujol.weathermap.domain.use_case.GetLocationCoordinatesUseCase
import com.pujol.weathermap.domain.use_case.GetSearchHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val getLocationCoordinatesUseCase: GetLocationCoordinatesUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val addSearchHistoryItemUseCase: AddSearchHistoryItemUseCase,
    private val deleteSearchHistoryItemUseCase: DeleteSearchHistoryItemUseCase
) :
    ViewModel() {
    var state by mutableStateOf(SearchLocationState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getSearchHistory()
    }

    fun onEvent(event: SearchLocationEvent) {
        when (event) {
            is SearchLocationEvent.OnSetQuery -> {
                state = state.copy(
                    textFieldValue = event.textFieldValue,
                    showSearchHistory = getStatusShowList(
                        event.textFieldValue,
                        state.locationCoordinates,
                        isCallService = state.isCallService
                    ),
                    iconResource = getIconResource(
                        textFieldValue = event.textFieldValue,
                        state.locationCoordinates,
                        isCallService = state.isCallService
                    )
                )
                getSearchHistory()
            }
            is SearchLocationEvent.OnSearchClick -> {
                if (state.textFieldValue.text.isEmpty()) {
                    Toast.makeText(event.context, event.context.getString(R.string.enter_city), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    getLocationCoordinates(state.textFieldValue.text)
                }
            }
            is SearchLocationEvent.OnNavigateToMap -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigatePop(event.locationCoordinate))
                    addSearchHistoryItemUseCase(event.locationCoordinate)
                }
            }
            is SearchLocationEvent.OnNavigateUp -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigatePop(LocationCoordinate()))
                }
            }
        }
    }

    private fun getLocationCoordinates(category: String) {
        viewModelScope.launch {
            getLocationCoordinatesUseCase(category).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let {
                            state = state.copy(
                                locationCoordinates = it,
                                isEmptyLocationCoordinate = it.isEmpty(),
                                isCallService = true,
                                showSearchHistory = getStatusShowList(
                                    textFieldValue = state.textFieldValue,
                                    it,
                                    isCallService = true
                                ),
                                iconResource = getIconResource(
                                    textFieldValue = state.textFieldValue,
                                    it,
                                    isCallService = true
                                )
                            )
                        }
                        if (state.isEmptyLocationCoordinate)
                            addSearchHistoryItem(LocationCoordinate(name = category))
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

    private fun getSearchHistory() {
        viewModelScope.launch {
            getSearchHistoryUseCase().collect { response ->
                state =
                    state.copy(searchHistory = response, isEmptySearchHistory = response.isEmpty())
            }
        }
    }

    private fun addSearchHistoryItem(locationCoordinate: LocationCoordinate) {
        viewModelScope.launch {
            if (state.searchHistory.contains(locationCoordinate)) {
                deleteSearchHistoryItemUseCase(locationCoordinate)
            }
            addSearchHistoryItemUseCase(locationCoordinate)
        }
    }

    private fun getIconResource(
        textFieldValue: TextFieldValue,
        locationCoordinates: List<LocationCoordinate>,
        isCallService: Boolean
    ): Int {
        return if (textFieldValue.text.isEmpty() || (locationCoordinates.isEmpty() && !isCallService)) R.drawable.ic_history else R.drawable.ic_location
    }

    private fun getStatusShowList(
        textFieldValue: TextFieldValue,
        locationCoordinates: List<LocationCoordinate>,
        isCallService: Boolean
    ): Boolean {
        return (textFieldValue.text.isEmpty() || (locationCoordinates.isEmpty() && !isCallService))
    }
}