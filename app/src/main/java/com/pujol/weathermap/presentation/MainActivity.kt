package com.pujol.weathermap.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.pujol.weathermap.domain.model.LocationCoordinate
import com.pujol.weathermap.presentation.map.MapScreen
import com.pujol.weathermap.presentation.searchLocation.SearchLocationScreen
import com.pujol.weathermap.ui.navigation.Route
import com.pujol.weathermap.ui.theme.WeatherMapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherMapTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    val activity = (LocalContext.current as Activity)

                    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                        AnimatedNavHost(
                            navController = navController,
                            startDestination = Route.MAP,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            composable(Route.SEARCH_LOCATION) {
                                SearchLocationScreen(
                                    onSearchLocation = { data ->
                                        navController.previousBackStackEntry?.savedStateHandle?.set(
                                            "searchLocation",
                                            Gson().toJson(data)
                                        )
                                        navController.popBackStack()
                                    })
                            }
                            composable(Route.MAP) { backStackEntry ->
                                val searchLocation by backStackEntry.savedStateHandle.getLiveData<String>(
                                    "searchLocation"
                                ).observeAsState()
                                MapScreen(
                                    searchLocation = Gson().fromJson(
                                        searchLocation,
                                        LocationCoordinate::class.java
                                    ), onSearchClick = {
                                        navController.navigate(Route.SEARCH_LOCATION)
                                        backStackEntry.savedStateHandle.remove<String>("searchLocation")
                                    },
                                onFinishApp = {
                                    activity.finish()
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}