package com.pujol.weathermap.presentation.map

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SwipeableDefaults.AnimationSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.pujol.weathermap.R
import com.pujol.weathermap.core.util.LifecycleEventListener
import com.pujol.weathermap.core.util.UiEvent
import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.presentation.map.weather.WeatherScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    searchLocation: LocationCoordinate?,
    onSearchClick: () -> Unit,
    onFinishApp: () -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = AnimationSpec
    )
    val scope = rememberCoroutineScope()

    BackHandler {
        if (sheetState.isVisible) {
            scope.launch {
                sheetState.hide()
            }
        } else {
            onFinishApp()
        }
    }

    LaunchedEffect(context) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onSearchClick()
                }
                else -> {}
            }
        }
    }

    LocalLifecycleOwner.current.lifecycle.LifecycleEventListener {
        when (it) {
            Lifecycle.Event.ON_CREATE -> {
                if (state.stateLatLng == false && !searchLocation?.name.isNullOrEmpty()) {
                    viewModel.onEvent(
                        MapEvent.OnsetLatLng(
                            LatLng(
                                searchLocation?.latitude ?: 0.0,
                                searchLocation?.longitude ?: 0.0
                            ),
                            boolean = true
                        )
                    )
                    scope.launch {
                        sheetState.show()
                    }
                }
            }
            else -> {}
        }
    }

    ModalBottomSheetLayout(sheetState = sheetState,
        sheetElevation = 30.dp,
        sheetContent = {
            WeatherScreen(
                locationWeather = state.locationWeather,
                modifier = Modifier,
                timeStamp = state.timeStamp!!,
                isLoading = state.isLoading
            )
        }) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (search, googleMap) = createRefs()
            val cameraPosition = CameraPositionState(
                position = CameraPosition.fromLatLngZoom(
                    state.latLng,
                    5f
                )
            )
            GoogleMap(modifier = Modifier
                .constrainAs(googleMap) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                cameraPositionState = cameraPosition,
                onMapClick = { latLng ->
                    viewModel.onEvent(MapEvent.OnsetLatLng(latLng))
                    scope.launch {
                        sheetState.show()
                    }
                }) {
                if(state.latLng != LatLng(0.0,0.0)) {
                    Marker(
                        state = MarkerState(state.latLng),
                        title = "${state.latLng}"
                    )
                }
            }
            Card(elevation = 16.dp,
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color.White,
                modifier = Modifier
                    .clickable {
                        viewModel.onEvent(MapEvent.OnNavigateToSearch)
                    }
                    .height(50.dp)
                    .constrainAs(search) {
                        top.linkTo(googleMap.top, margin = 16.dp)
                        start.linkTo(googleMap.start, margin = 16.dp)
                        end.linkTo(googleMap.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }) {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (search_icon, query, close) = createRefs()
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .constrainAs(search_icon) {
                                start.linkTo(parent.start, margin = 16.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )

                    Text(
                        text = if (searchLocation?.name?.isNotEmpty() == true) {
                            searchLocation.name
                        } else {
                            stringResource(R.string.look_climate_locality)
                        },
                        modifier = Modifier.constrainAs(query) {
                            top.linkTo(parent.top)
                            start.linkTo(search_icon.end, margin = 8.dp)
                            end.linkTo(close.start, margin = 8.dp)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                            .constrainAs(close) {
                                end.linkTo(parent.end, margin = 16.dp)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            }
        }
    }
}