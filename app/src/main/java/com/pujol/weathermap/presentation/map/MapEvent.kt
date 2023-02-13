package com.pujol.weathermap.presentation.map

import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    data class OnsetLatLng(val latLng: LatLng, val boolean: Boolean? = null) : MapEvent()
    object OnNavigateToSearch : MapEvent()
}