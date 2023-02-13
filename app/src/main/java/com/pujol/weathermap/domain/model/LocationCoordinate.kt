package com.pujol.weathermap.domain.model

data class LocationCoordinate(
    val country: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val name: String? = "",
    val state: String? = ""
)